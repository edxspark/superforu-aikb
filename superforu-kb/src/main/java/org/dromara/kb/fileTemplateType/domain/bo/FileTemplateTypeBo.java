package org.dromara.kb.fileTemplateType.domain.bo;

import org.dromara.kb.fileTemplateType.domain.FileTemplateType;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 文档模板类型业务对象 kb_file_template_type
 *
 * @author Lion Li
 * @date 2023-12-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FileTemplateType.class, reverseConvertGenerate = false)
public class FileTemplateTypeBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 排序
     */
    private Integer sort;


}
