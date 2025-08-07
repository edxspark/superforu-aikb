package org.dromara.com.superModuleConfig.domain.vo;

import org.dromara.com.superModuleConfig.domain.ComSuperModuleConfig;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 超级模块配置视图对象 com_super_module_config
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComSuperModuleConfig.class)
public class ComSuperModuleConfigVo implements Serializable {

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
     * 用户等级id关联
     */
    @ExcelProperty(value = "用户等级id关联")
    private Long linkUserEquityId;

    /**
     * 是否初始化（0：初始化 1:未初始化）
     */
    @ExcelProperty(value = "是否初始化", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=：初始化,1=:未初始化")
    private Long status;

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
     * 打开方式
     */
    private String openWay;


}
