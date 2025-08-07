package org.dromara.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付渠道枚举
 *
 * @author Moks
 */
@Getter
@AllArgsConstructor
public enum PayWayEnum {

    WXPAY("wxpay", "微信支付"),
    ALIPAY("alipay", "支付宝支付");

    private final String key;
    private final String value;

}
