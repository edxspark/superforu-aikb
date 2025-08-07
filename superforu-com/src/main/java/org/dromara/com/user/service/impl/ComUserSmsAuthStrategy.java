package org.dromara.com.user.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.com.user.domain.ComLoginBody;
import org.dromara.com.user.domain.ComUser;
import org.dromara.com.user.mapper.ComUserMapper;
import org.dromara.com.user.service.ComLoginService;
import org.dromara.com.user.service.IComUserAuthStrategy;
import org.dromara.com.utils.common.ErrorMsg;
import org.dromara.com.utils.loginUtil.LoginUtil;
import org.dromara.com.utils.loginUtil.vo.LoginVo;
import org.dromara.common.core.constant.Constants;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.enums.LoginType;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.core.exception.user.CaptchaExpireException;
import org.dromara.common.core.exception.user.UserException;
import org.dromara.common.core.utils.MessageUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.ValidatorUtils;
import org.dromara.common.core.validate.auth.SmsGroup;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.SysClient;
import org.springframework.stereotype.Service;

/**
 * 短信认证策略
 *
 * @author JackLiao
 */
@Slf4j
@Service("sms" + IComUserAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class ComUserSmsAuthStrategy implements IComUserAuthStrategy {

    private final ComLoginService loginService;
    private final ComUserMapper baseMapper;

    @Override
    public void validate(ComLoginBody loginBody) {
        ValidatorUtils.validate(loginBody, SmsGroup.class);
    }

    @Override
    public LoginVo login(String clientId, ComLoginBody loginBody, SysClient client) {
        String phoneNumber = loginBody.getPhoneNumber();
        String smsCode = loginBody.getSmsCode();

        // 通过手机号查找用户
        ComUser user = loadUserByPhoneNumber(phoneNumber);

        loginService.checkLogin(LoginType.SMS, user.getUserAccount(), () -> !validateSmsCode(phoneNumber, smsCode));
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        LoginUser loginComUser = loginService.buildLoginUser(user);
        loginComUser.setClientKey(client.getClientKey());
        loginComUser.setDeviceType(client.getDeviceType());
        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginHelper.CLIENT_KEY, clientId);
        // 生成token
        LoginUtil.login(loginComUser, model);

//        loginService.recordLogininfor(loginComUser.getTenantId(), user.getUserName(), RedisKeyConstants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
//        loginService.recordLoginInfo(user.getUserId());

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(clientId);
        return loginVo;
    }

    /**
     * 校验短信验证码
     */
    private boolean validateSmsCode(String phoneNumber, String smsCode) {
        String code = RedisUtils.getCacheObject(GlobalConstants.LOGIN_CODE_KEY + phoneNumber);
        if (StringUtils.isBlank(code)) {
            loginService.recordLoginInfo(phoneNumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(smsCode);
    }

    private ComUser loadUserByPhoneNumber(String phoneNumber) {
        ComUser user = baseMapper.selectOne(new LambdaQueryWrapper<ComUser>()
            .eq(ComUser::getUserAccount, phoneNumber));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", phoneNumber);
            throw new BaseException(ErrorMsg.ERR_COM_USER_NOT_EXIST.getMessage());
        }
        return user;
    }

}
