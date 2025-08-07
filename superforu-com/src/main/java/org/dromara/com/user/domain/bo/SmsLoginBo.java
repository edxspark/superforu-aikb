package org.dromara.com.user.domain.bo;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 短信登录对象
 *
 * @author Lion Li
 */

@Data
public class SmsLoginBo {

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 短信类型
     */
    @NotBlank(message = "短信类型不可为空")
    private String smsType;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不可为空")
    private String phoneNumber;

}
