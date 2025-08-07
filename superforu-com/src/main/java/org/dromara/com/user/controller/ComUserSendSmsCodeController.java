package org.dromara.com.user.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.com.user.domain.bo.SmsLoginBo;
import org.dromara.com.user.service.IComUserSmsCodeStrategy;
import org.dromara.common.core.constant.UserConstants;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.MessageUtils;
import org.dromara.system.domain.SysClient;
import org.dromara.system.service.ISysClientService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户发送验证码
 *
 * @author JackLiao
 */
@Slf4j
@SaIgnore
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/user/sms")
public class ComUserSendSmsCodeController {

    private final ISysClientService clientService;




    /**
     * 用户发送验证码
     *
     * @param smsLoginBo 短信信息
     * @return 结果
     */
    @PostMapping("/send-sms-code")
    public R<Void> sendSms(@RequestHeader("Clientid") String clientId,@Validated @RequestBody SmsLoginBo smsLoginBo) {
        // 授权类型和客户端id
        smsLoginBo.setClientId(clientId);
        SysClient client = clientService.queryByClientId(clientId);
        // 查询不到 client
        if (ObjectUtil.isNull(client)) {
            log.info("客户端id异常!.", clientId);
            return R.fail(MessageUtils.message("auth.grant.type.error"));
        } else if (!UserConstants.NORMAL.equals(client.getStatus())) {
            return R.fail(MessageUtils.message("auth.grant.type.blocked"));
        }
        IComUserSmsCodeStrategy.sendCode(smsLoginBo,client);
        return R.ok();
    }
}
