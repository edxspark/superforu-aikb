package org.dromara.kb.fileTemplateType.domain.vo;

import org.dromara.kb.fileTemplateType.domain.FileTemplateType;
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
 * 文档模板类型视图对象 kb_file_template_type
 *
 * @author Lion Li
 * @date 2023-12-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FileTemplateType.class)
public class FileTemplateTypeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
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
    private Integer sort;


}
