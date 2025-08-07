package org.dromara.com.user.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.com.user.domain.ComLoginBody;
import org.dromara.com.user.domain.ComRegisterBody;
import org.dromara.com.user.service.ComLoginService;
import org.dromara.com.user.service.IComUserAuthStrategy;
import org.dromara.com.user.service.IComUserRegisterService;
import org.dromara.com.utils.loginUtil.vo.LoginVo;
import org.dromara.common.core.constant.UserConstants;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.MessageUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.system.domain.SysClient;
import org.dromara.system.service.ISysClientService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 认证
 *
 * @author JackLiao
 */
@Slf4j
@SaIgnore
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/user/auth")
public class ComUserAuthController {


    private final ISysClientService clientService;

    private final IComUserRegisterService iComUserRegisterService;

    private final ComLoginService comLoginService;


    /**
     * 注册方法
     *
     * @param comRegisterBody 注册信息
     * @return 结果
     */
    @PostMapping("/register")
    public R<LoginVo> register(@RequestHeader("Clientid") String clientId,@Validated @RequestBody ComRegisterBody comRegisterBody) {

        // 授权类型和客户端id
        comRegisterBody.setClientId(clientId);
        SysClient client = clientService.queryByClientId(clientId);
        // 查询不到 client
        if (ObjectUtil.isNull(client)) {
            log.info("客户端id: {}异常!.", clientId);
            return R.fail(MessageUtils.message("auth.grant.type.error"));
        } else if (!UserConstants.NORMAL.equals(client.getStatus())) {
            return R.fail(MessageUtils.message("auth.grant.type.blocked"));
        }
        // 注册
        return R.ok(iComUserRegisterService.register(comRegisterBody, client));
    }

    /**
     * 登录方法
     *
     * @param comLoginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public R<LoginVo> login(@RequestHeader("Clientid") String clientId,@Validated @RequestBody ComLoginBody comLoginBody) {

        // 授权类型和客户端id
        comLoginBody.setClientId(clientId);
        String grantType = comLoginBody.getGrantType();
        SysClient client = clientService.queryByClientId(clientId);
        // 查询不到 client 或 client 内不包含 grantType
        if (ObjectUtil.isNull(client) || !StringUtils.contains(client.getGrantType(), grantType)) {
            log.info("客户端id: {} 认证类型：{} 异常!.", clientId, grantType);
            return R.fail(MessageUtils.message("auth.grant.type.error"));
        } else if (!UserConstants.NORMAL.equals(client.getStatus())) {
            return R.fail(MessageUtils.message("auth.grant.type.blocked"));
        }
        // 登录
        return R.ok(IComUserAuthStrategy.login(comLoginBody, client));
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public R<Void> logout() {
        comLoginService.logout();
        return R.ok("退出成功");
    }
}
