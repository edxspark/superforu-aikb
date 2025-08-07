package org.dromara.com.user.service.impl;

import cn.dev33.satoken.secure.BCrypt;
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
import org.dromara.common.core.exception.user.CaptchaException;
import org.dromara.common.core.exception.user.CaptchaExpireException;
import org.dromara.common.core.exception.user.UserException;
import org.dromara.common.core.utils.MessageUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.ValidatorUtils;
import org.dromara.common.core.validate.auth.PasswordGroup;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.web.config.properties.CaptchaProperties;
import org.dromara.system.domain.SysClient;
import org.springframework.stereotype.Service;

/**
 * 密码认证策略
 *
 * @author JackLiao
 */
@Slf4j
@Service("password" + IComUserAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class ComUserPasswordAuthStrategy implements IComUserAuthStrategy {

    private final CaptchaProperties captchaProperties;
    private final ComLoginService loginService;
    private final ComUserMapper userMapper;


    @Override
    public void validate(ComLoginBody comLoginBody) {
        ValidatorUtils.validate(comLoginBody, PasswordGroup.class);
    }


    /**
     * 使用账号密码进行登录
     * 1.校验登录账号是否存在
     * 2.登录校验（loginService.checkLogin）
     * 3.构建用户登录信息
     * 4.生成token
     * 5.记录登录信息
     * @param clientId
     * @param comLoginBody
     * @param client
     * @return
     */
    @Override
    public LoginVo login(String clientId, ComLoginBody comLoginBody, SysClient client) {
        String userAccount = comLoginBody.getPhoneNumber();
        String password = comLoginBody.getPassword();

        //1.校验登录账号是否存在
        ComUser user = loadUserByUserAccount(userAccount);
        //2.登录校验
        loginService.checkLogin(LoginType.PASSWORD, userAccount, () -> !BCrypt.checkpw(password, user.getUserPsw()));
        //3.此处可根据登录用户的数据不同 自行创建 loginUser
        LoginUser loginComUser = loginService.buildLoginUser(user);
        loginComUser.setClientKey(client.getClientKey());
        loginComUser.setDeviceType(client.getDeviceType());
        loginComUser.setUsername(userAccount);
        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginUtil.CLIENT_KEY, clientId);
        // 生成token
        LoginUtil.login(loginComUser, model);

        //5.记录登录信息
        loginService.recordLoginInfo(user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(clientId);
        return loginVo;
    }


    //根据账号获取用户信息
    private ComUser loadUserByUserAccount(String userAccount) {
        ComUser user = userMapper.selectOne(new LambdaQueryWrapper<ComUser>()
                .eq(ComUser::getUserAccount,userAccount));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", userAccount);
            throw new BaseException(ErrorMsg.ERR_COM_USER_NOT_EXIST.getMessage());
        }
        return user;
    }

}
