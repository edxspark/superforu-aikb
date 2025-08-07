package org.dromara.kb.fileType.domain.vo;

import org.dromara.kb.fileType.domain.FileType;
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
 * 文件类型视图对象 kb_file_type
 *
 * @author Lion Li
 * @date 2023-12-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FileType.class)
public class FileTypeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

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
     * 序号
     */
    @ExcelProperty(value = "序号")
    private Integer sort;

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
