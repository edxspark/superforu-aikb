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
public class FolderFileRenameBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 文件名称
     */
    @Size(message= "知识库名称不能超过个 {max} 字符",min = 1,max = 64, groups = { AddGroup.class, EditGroup.class})
    @NotBlank(message = "文件名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fileName;

    /**
     * 知识库ID
     */
    private Long linkKmId;

    /**
     * 知识文档树数据
     */
    private String fileKmTreeData;

}
