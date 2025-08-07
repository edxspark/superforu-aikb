package org.dromara.com.utils.msgUtil;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.com.utils.constants.Constants;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.dromara.sms4j.provider.enumerate.SupplierType;
import org.dromara.system.service.impl.SysConfigServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

/**
 * 短信发送工具类
 *
 * @author Moks
 */
@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/msg")
public class MsgUtil {

    private final SysConfigServiceImpl sysConfigService;

    /**
     * 发送短信验证码
     * 1. 参数设置
     * 2. 调用短信服务
     *
     * @param phoneNumber 手机号码
     * @author Moks
     */
    @SaIgnore
    @GetMapping("/sendCode")
    public R<String> sendCode(String phoneNumber) {
        // 1. 参数设置
        String code = String.valueOf(RandomUtil.randomInt(100000, 999999));
        SmsBlend smsBlend = SmsFactory.createSmsBlend(SupplierType.ALIBABA);
        LinkedHashMap<String, String> map = new LinkedHashMap<>(1);
        map.put("code", code);
        String templateId = sysConfigService.selectConfigByKey("com.msg.template.code");

        // 2. 调用短信服务
        SmsResponse smsResponse = smsBlend.sendMessage(phoneNumber, templateId, map);
        log.info("短信发送服务返回结果:{}", JsonUtils.toJsonString(smsResponse));
        if (!Constants.OK.equals(smsResponse.getCode())) {
            throw new BaseException("短信发送失败，错误："+JsonUtils.parseMap(JsonUtils.toJsonString(smsResponse)).getStr("message"));
        }
        return R.ok(code);
    }

}
