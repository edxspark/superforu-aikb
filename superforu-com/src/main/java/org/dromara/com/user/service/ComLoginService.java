package org.dromara.com.user.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.com.user.domain.ComUser;
import org.dromara.com.utils.loginUtil.LoginUtil;
import org.dromara.common.core.constant.Constants;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.constant.TenantConstants;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.enums.LoginType;
import org.dromara.common.core.enums.TenantStatus;
import org.dromara.common.core.enums.UserType;
import org.dromara.common.core.exception.user.UserException;
import org.dromara.common.core.utils.*;
import org.dromara.common.log.event.LogininforEvent;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.tenant.exception.TenantException;
import org.dromara.common.tenant.helper.TenantHelper;
import org.dromara.system.domain.vo.SysTenantVo;
import org.dromara.system.service.ISysPermissionService;
import org.dromara.system.service.ISysSocialService;
import org.dromara.system.service.ISysTenantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * 登录校验方法
 *
 * @author Liaojialin
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ComLoginService {

    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;

    private final ISysTenantService tenantService;





    /**
     * 退出登录
     */
    public void logout() {
        try {
            LoginUser loginComUser = LoginUtil.getLoginUser();
            recordLoginInfo(loginComUser.getUsername(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
        } catch (NotLoginException ignored) {
        } finally {
            try {
                StpUtil.logout();
            } catch (NotLoginException ignored) {
            }
        }
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    public void recordLoginInfo(String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }


    /**
     * 构建登录用户
     */
    public LoginUser buildLoginUser(ComUser user) {
        LoginUser loginComUser = new LoginUser();
        loginComUser.setTenantId(user.getTenantId());
        loginComUser.setUserId(user.getId());
        loginComUser.setNickname(user.getUserName());
        loginComUser.setUserType(UserType.SYS_USER.getUserType());//文档用户默认为pc用户
        return loginComUser;
    }


    /**
     * 登录校验
     */
    public void checkLogin(LoginType loginType, String userAccount, Supplier<Boolean> supplier) {
        String errorKey = GlobalConstants.PWD_ERR_CNT_KEY + userAccount;
        String loginCodeKey = GlobalConstants.LOGIN_CODE_KEY + userAccount;
        String loginCodeValue = RedisUtils.getCacheObject(loginCodeKey);

        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
        int errorNumber = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
        // 锁定时间内登录 则踢出
        if (errorNumber >= maxRetryCount) {
            recordLoginInfo(userAccount, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        if (supplier.get()) {
            // 错误次数递增
            errorNumber++;
            RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
            // 达到规定错误次数 则锁定登录
            if (errorNumber >= maxRetryCount) {
                recordLoginInfo( userAccount, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到规定错误次数
                recordLoginInfo( userAccount, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber));
                throw new UserException(loginType.getRetryLimitCount(), errorNumber);
            }
        }

        // 登录成功 清空错误次数，并清除redis内的短信验证码
        List<String> clearKey = new ArrayList<>(ListUtil.of(errorKey));
        if(LoginType.SMS.equals(loginType)){
            clearKey.add(loginCodeKey);
        }
        RedisUtils.deleteObject(clearKey);
    }

    /**
     * 校验租户
     *
     * @param tenantId 租户ID
     */
    public void checkTenant(String tenantId) {
        if (!TenantHelper.isEnable()) {
            return;
        }
        if (TenantConstants.DEFAULT_TENANT_ID.equals(tenantId)) {
            return;
        }
        if (StringUtils.isBlank(tenantId)) {
            throw new TenantException("tenant.number.not.blank");
        }
        SysTenantVo tenant = tenantService.queryByTenantId(tenantId);
        if (ObjectUtil.isNull(tenant)) {
            log.info("登录租户：{} 不存在.", tenantId);
            throw new TenantException("tenant.not.exists");
        } else if (TenantStatus.DISABLE.getCode().equals(tenant.getStatus())) {
            log.info("登录租户：{} 已被停用.", tenantId);
            throw new TenantException("tenant.blocked");
        } else if (ObjectUtil.isNotNull(tenant.getExpireTime())
            && new Date().after(tenant.getExpireTime())) {
            log.info("登录租户：{} 已超过有效期.", tenantId);
            throw new TenantException("tenant.expired");
        }
    }

}
