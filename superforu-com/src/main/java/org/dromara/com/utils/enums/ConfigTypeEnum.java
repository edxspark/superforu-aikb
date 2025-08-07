package org.dromara.com.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户配置类型
 *
 * @author JackLiao
 */
@Getter
@AllArgsConstructor
public enum ConfigTypeEnum {

    QUICK_ENTRANCE(0, "快捷入口"),

    RECENT_VISIT(1, "最近访问"),

    SUPER_MODULE(2, "超级模块");

    private final Integer value;

    private final String description;

}
