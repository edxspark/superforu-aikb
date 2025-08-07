package org.dromara.kb.folderFile.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.kb.folderFile.domain.FolderFile;

/**
 * 文件夹&文件业务对象 kb_folder_file
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolderFile.class, reverseConvertGenerate = false)
public class FolderFileTemplateBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 模版ID
     */
    @NotBlank(message = "模版ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateId;

    /**
     * 父类ID
     */
    @NotNull(message = "父类ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;


    /**
     * 知识库ID
     */
    @NotNull(message = "知识库ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkKmId;
}
