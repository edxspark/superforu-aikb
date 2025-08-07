package org.dromara.com.quickEntranceConfig.domain.vo;

import org.dromara.com.quickEntranceConfig.domain.ComQuickEntranceConfig;
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
 * 快捷入口配置视图对象 com_quick_entrance_config
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComQuickEntranceConfig.class)
public class ComQuickEntranceConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Integer sort;

    /**
     * 文件类型id关联
     */
    @ExcelProperty(value = "文件类型id关联")
    private Long linkFileTypeId;

    /**
     * 是否初始化（0：初始化 1:未初始化）
     */
    @ExcelProperty(value = "是否初始化", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=：初始化,1=:未初始化")
    private Integer status;

    /**
     * 类型名称
     */
    @ExcelProperty(value = "类型名称")
    private String name;

    /**
     * 类型ID
     */
    @ExcelProperty(value = "类型ID")
    private String code;


    /**
     * 类型ICON
     */
    @ExcelProperty(value = "类型ICON")
    private String icon;

    /**
     * 类型颜色
     */
    @ExcelProperty(value = "类型颜色")
    private String color;

    /**
     * 后缀名
     */
    @ExcelProperty(value = "后缀名")
    private String attrType;


}
