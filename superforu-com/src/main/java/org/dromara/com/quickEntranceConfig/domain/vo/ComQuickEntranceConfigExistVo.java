package org.dromara.com.quickEntranceConfig.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.com.quickEntranceConfig.domain.ComQuickEntranceConfig;

import java.io.Serial;
import java.io.Serializable;


/**
 * 快捷入口配置是否被添加视图对象 com_quick_entrance_config
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
@AutoMapper(target = ComQuickEntranceConfig.class)
public class ComQuickEntranceConfigExistVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 排序
     */
    private Integer sort;


    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型ID
     */
    private String code;


    /**
     * 类型ICON
     */
    private String icon;

    /**
     * 类型颜色
     */
    private String color;

    /**
     * 后缀名
     */
    private String attrType;

    /**
     * 是否已被添加（0：已添加 1:未添加）
     */
    private Integer existStatus;

}
