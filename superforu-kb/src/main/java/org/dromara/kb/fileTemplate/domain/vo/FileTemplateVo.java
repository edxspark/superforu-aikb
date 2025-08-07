package org.dromara.kb.fileTemplate.domain.vo;

import org.dromara.kb.fileTemplate.domain.FileTemplate;
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
 * 文档模板视图对象 kb_file_template
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FileTemplate.class)
public class FileTemplateVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 模板名称
     */
    @ExcelProperty(value = "模板名称")
    private String name;

    /**
     * 文件类型
     */
    @ExcelProperty(value = "文件类型")
    @ExcelDictFormat(dictType = "file_type")
    private String fileTypeCode;

    /**
     * 文件类型名称
     */
    @ExcelProperty(value = "文件类型名称")
    private String fileTypeName;

    /**
     * 引用次数
     */
    @ExcelProperty(value = "引用次数")
    private Long useCount;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "kb_status")
    private Long status;

    /**
     * 模板类型ID
     */
    @ExcelProperty(value = "模板类型ID")
    private Long linkFileTemplateTypeId;

    /**
     * 模板类型名称
     */
    @ExcelProperty(value = "模板类型名称")
    private String linkFileTemplateTypeName;

    /**
     * 内容
     */
    private String attrContent;

    /**
     * 封面地址
     */
    private String picUrl;

    /**
     * 最后时间
     */
    private Date updateTime;

    /**
     * 编辑器地址
     */
    private String editorURL;

}
