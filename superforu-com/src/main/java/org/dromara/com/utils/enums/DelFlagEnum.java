package org.dromara.com.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 删除标识
 *
 * @author JackLiao
 */
@Getter
@AllArgsConstructor
public enum DelFlagEnum {

    UN_DELETED(0, "未删除"),


    DELETED(2, "已删除");

    private final int value;
    private final String description;


}
