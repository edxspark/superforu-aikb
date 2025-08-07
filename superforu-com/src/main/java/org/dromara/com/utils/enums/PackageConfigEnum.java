package org.dromara.com.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权益配置
 *
 */
@Getter
@AllArgsConstructor
public enum PackageConfigEnum {
    /**
     * 专业会员
     */
   MEMBER_PACKAGE ("memberPackage", "专业会员"),

    /**
     * AI账户充值
     */
    AI_TOKEN_PACKAGE("aiTokenPackage", "AI账户充值");


    private final String key;
    private final String value;

}
