package org.dromara.com.user.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.dromara.common.core.constant.UserConstants;
import org.dromara.common.core.validate.auth.*;
import org.hibernate.validator.constraints.Length;

/**
 * 用户登录对象
 *
 * @author JackLiao
 */

@Data
public class ComLoginBody {

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 授权类型
     */
    @NotBlank(message = "{auth.grant.type.not.blank}")
    private String grantType;

    /**
     * 用户密码
     */
    @NotBlank(message = "{user.password.not.blank}", groups = {PasswordGroup.class})
    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH, message = "{user.password.length.valid}", groups = {PasswordGroup.class})
    private String password;

    /**
     * 手机号
     */
    @NotBlank(message = "{user.phoneNumber.not.blank}", groups = {SmsGroup.class})
    private String phoneNumber;

    /**
     * 短信code
     */
    @NotBlank(message = "{sms.code.not.blank}", groups = {SmsGroup.class})
    private String smsCode;

}
