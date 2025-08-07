package org.dromara.com.user.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.com.inviteHistory.domain.ComInviteHistory;
import org.dromara.com.inviteHistory.domain.bo.ComInviteHistoryBo;
import org.dromara.com.inviteHistory.mapper.ComInviteHistoryMapper;
import org.dromara.com.inviteHistory.service.IComInviteHistoryService;
import org.dromara.com.user.domain.ComRegisterBody;
import org.dromara.com.user.domain.ComUser;
import org.dromara.com.user.mapper.ComUserMapper;
import org.dromara.com.user.service.ComLoginService;
import org.dromara.com.user.service.IComUserAuthStrategy;
import org.dromara.com.user.service.IComUserRegisterService;
import org.dromara.com.userConfig.service.IComUserConfigService;
import org.dromara.com.userPackagePurchase.service.impl.ComUserPackagePurchaseServiceImpl;
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
import org.dromara.common.core.validate.auth.PasswordGroup;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.SysClient;
import org.dromara.system.service.impl.SysConfigServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

import org.dromara.com.utils.common.ErrorMsg;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户注册
 *
 * @author JackLiao
 */
@Slf4j
@Service("register" + IComUserAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class ComUserRegisterService implements IComUserRegisterService {

    private final ComLoginService loginService;

    private final SysConfigServiceImpl sysConfigService;


    private final IComUserConfigService comUserConfigService;

    private final IComInviteHistoryService comInviteHistoryService;

    private final ComUserMapper baserMapper;

    private final ComInviteHistoryMapper comInviteHistoryMapper;

    private final ComUserPackagePurchaseServiceImpl comUserPackagePurchaseService;



    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;


    /**
     * 用户注册
     * 1.校验用户参数必填项
     * 2.校验该用户是否已存在
     * 3.注册校验
     *      3.1校验注册短信验证码
     *      3.2校验验证失败次数
     * 4.新增用户
     *      4.1新增用户基础信息
     *      4.2新增用户配置信息
     *      4.3新增用户邀请信息（若有邀请人）
     * 5.构建用户登录信息
     * 6.生成token
     * 7.被邀请处理
     * @param comLoginBody
     * @param client
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public LoginVo register(ComRegisterBody comLoginBody, SysClient client) {

        String phoneNumber = comLoginBody.getPhoneNumber();
        String smsCode = comLoginBody.getSmsCode();
        String password = comLoginBody.getPassword();
        String clientId = client.getClientId();
        //1.校验用户参数必填项
        validate(comLoginBody);

        //2.校验该用户是否已存在
        Boolean userExist = checkUserExist(phoneNumber);
        if(userExist){
            throw new BaseException(ErrorMsg.ERR_COM_USER_EXIST.getMessage());
        }

        //3注册校验
        checkRegister(LoginType.SMS, comLoginBody.getPhoneNumber(), () -> !validateRegisterSmsCode(phoneNumber, smsCode));

        ComUser add = new ComUser();
        add.setUserAccount(phoneNumber);
        add.setTheme(sysConfigService.selectConfigByKey("com.default.theme"));
        add.setLanguage(sysConfigService.selectConfigByKey("com.default.language"));
        add.setUserName(sysConfigService.selectConfigByKey("com.default.user.nickname"));
        add.setSignature(sysConfigService.selectConfigByKey("com.default.user.sign"));
        add.setPicUrl(sysConfigService.selectConfigByKey("com.default.user.avatar.url"));
        add.setUserPsw(BCrypt.hashpw(password));

        //4.1.新增用户信息
        boolean b = baserMapper.insert(add) > 0;
        //4.2新增用户配置
        if (b){
            Long userId = add.getId();
            comUserConfigService.insertByBo(userId);
            if(ObjectUtil.isNotNull(comLoginBody.getLinkInviterId())){//邀请者用户id不为空
                ComInviteHistoryBo comInviteHistoryBo = new ComInviteHistoryBo();
                comInviteHistoryBo.setLinkInviterId(comLoginBody.getLinkInviterId());
                comInviteHistoryBo.setLinkInviteeId(userId);
                //4.3新增用户邀请人
                comInviteHistoryService.insertByBo(comInviteHistoryBo);
            }

        }else{
            throw new BaseException(ErrorMsg.ERR_COM_INSERT_ERR.getMessage());
        }

        // 5.此处可根据注册用户的数据不同 自行创建 loginUser
        LoginUser loginComUser = loginService.buildLoginUser(add);
        loginComUser.setClientKey(client.getClientKey());
        loginComUser.setDeviceType(client.getDeviceType());
        loginComUser.setUsername(phoneNumber);


        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginUtil.CLIENT_KEY, clientId);
        // 6.生成token
        LoginUtil.login(loginComUser, model);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(clientId);

        // 7.被邀请处理
        if(null != comLoginBody.getLinkInviterId() && (""+comLoginBody.getLinkInviterId()).length()>=6){
            int months = 3;
            // 邀请人（赠送三个人会员）
            Long inviteUserId = comLoginBody.getLinkInviterId();
            comUserPackagePurchaseService.giftToMember(inviteUserId,months);

            // 被邀请人（赠送三个人会员）
            Long userId = LoginHelper.getLoginUser().getUserId();
            comUserPackagePurchaseService.giftToMember(userId,months);

            // 记录邀请人获得权益记录
            ComInviteHistory comInviteHistoryAdd = new ComInviteHistory();
            comInviteHistoryAdd.setLinkInviterId(inviteUserId);
            comInviteHistoryAdd.setLinkInviteeId(userId);
            comInviteHistoryAdd.setGetDetail("3个月会员权益");
            comInviteHistoryMapper.insert(comInviteHistoryAdd);

        }

        // 8. 创建知首个知识库



        return loginVo;
    }


    private Boolean checkUserExist(String userAccount) {
        ComUser user = baserMapper.selectOne(new LambdaQueryWrapper<ComUser>()
                .eq(ComUser::getUserAccount,userAccount));
        if (ObjectUtil.isNotNull(user)) {
            return true;
        }
        return false;
    }

    public void validate(ComRegisterBody comRegisterBody) {
        ValidatorUtils.validate(comRegisterBody, PasswordGroup.class);
    }

    /**
     * 校验注册短信验证码
     */
    private boolean validateRegisterSmsCode(String phoneNumber, String smsCode) {


//        String code = RedisUtils.getCacheObject(GlobalConstants.REGISTER_CODE_KEY + phoneNumber);
//        if (StringUtils.isBlank(code)) {
//            loginService.recordLoginInfo(phoneNumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
//            throw new CaptchaExpireException();
//        }
//        return code.equals(smsCode);
        return true;
    }


    /**
     * 注册校验
     */
    public void checkRegister(LoginType loginType, String userAccount, Supplier<Boolean> supplier) {
        String errorKey = GlobalConstants.PWD_ERR_CNT_KEY + userAccount;
        String registerCodeKey = GlobalConstants.REGISTER_CODE_KEY + userAccount;

        // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
        int errorNumber = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
        // 锁定时间内登录 则踢出
        if (errorNumber >= maxRetryCount) {
            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        if (supplier.get()) {
            // 错误次数递增
            errorNumber++;
            RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
            // 达到规定错误次数 则锁定注册
            if (errorNumber >= maxRetryCount) {
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到规定错误次数
                throw new UserException(loginType.getRetryLimitCount(), errorNumber);
            }
        }

        // 注册成功 清空错误次数，并清除redis内的短信验证码
        List<String> clearKey = ListUtil.of(errorKey, registerCodeKey);
        RedisUtils.deleteObject(clearKey);
    }
}
