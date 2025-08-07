package org.dromara.com.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 团队成员角色类型
 *
 * @author JackLiao
 */
@Getter
@AllArgsConstructor
public enum RoleTypes {
    /**
     * 查看者
     */
    VIEWER(0, "查看者"),

    /**
     * 编辑者
     */
    EDITOR(1, "编辑者"),

    /**
     * 管理者
     */
    ADMIN(2, "管理者");

    private final Integer value;
    private final String description;

}
