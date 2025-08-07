package org.dromara.com.superModuleConfig.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.com.superModuleConfig.domain.ComSuperModuleConfig;


import java.io.Serial;
import java.io.Serializable;


/**
 * 超级模块配置是否被添加视图对象 com_super_module_config
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComSuperModuleConfig.class)
public class ComSuperModuleConfigExistVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String name;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long sort;

    /**
     * 配置内容
     */
    @ExcelProperty(value = "配置内容")
    private String value;

    /**
     * 模块ICON
     */
    @ExcelProperty(value = "模块ICON")
    private String icon;

    /**
     * 模块颜色
     */
    @ExcelProperty(value = "模块颜色")
    private String color;

    /**
     * 是否已被添加（0：已添加 1:未添加）
     */
    private Integer existStatus;

    /**
     * 打开方式
     */
    private String openWay;
}
