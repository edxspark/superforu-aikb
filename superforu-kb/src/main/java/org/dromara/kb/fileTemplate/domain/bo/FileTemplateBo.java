package org.dromara.kb.fileTemplate.domain.bo;

import org.dromara.kb.fileTemplate.domain.FileTemplate;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 文档模板业务对象 kb_file_template
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FileTemplate.class, reverseConvertGenerate = false)
public class FileTemplateBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 文件类型
     */
    private String fileTypeCode;

    /**
     * 文件类型名称
     */
    private String fileTypeName;

    /**
     * 引用次数
     */
    private Long useCount;

    /**
     * 状态
     */
    private Long status;

    /**
     * 模板类型ID
     */
    private Long linkFileTemplateTypeId;

    /**
     * 后缀名
     */
    private String attrType;

    /**
     * 内容
     */
    private String attrContent;

    /**
     * 图片地址
     */
    private String picUrl;

}
