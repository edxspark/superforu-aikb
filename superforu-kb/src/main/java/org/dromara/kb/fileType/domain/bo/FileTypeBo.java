package org.dromara.kb.fileType.domain.bo;

import org.dromara.kb.fileType.domain.FileType;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 文件类型业务对象 kb_file_type
 *
 * @author Lion Li
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FileType.class, reverseConvertGenerate = false)
public class FileTypeBo extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 类型名称
     */
    @NotBlank(message = "类型名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 类型ID
     */
    @NotBlank(message = "类型ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String code;

    /**
     * 序号
     */
    @NotNull(message = "序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer sort;

    /**
     * 类型ICON
     */
    @NotBlank(message = "类型ICON不能为空", groups = { AddGroup.class, EditGroup.class })
    private String icon;

    /**
     * 类型颜色
     */
    @NotBlank(message = "类型颜色不能为空", groups = { AddGroup.class, EditGroup.class })
    private String color;

    /**
     * 后缀名
     */
    @NotBlank(message = "后缀名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String attrType;


}
