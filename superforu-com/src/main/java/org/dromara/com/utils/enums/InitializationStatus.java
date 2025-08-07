package org.dromara.com.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InitializationStatus {

    INITIALIZED(0, "初始化"),

    NOT_INITIALIZED(1, "未初始化");

    private final Integer value;
    private final String description;

}
