package org.dromara.kb.fileRecently.domain.bo;

import org.dromara.kb.fileRecently.domain.FileRecently;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 最近编辑业务对象 kb_file_recently
 *
 * @author Lion Li
 * @date 2023-12-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FileRecently.class, reverseConvertGenerate = false)
public class FileRecentlyBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 文件ID
     */
    @NotNull(message = "文件ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkFileId;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkUserId;


    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件摘要
     */
    private String fileAbstract;

}
