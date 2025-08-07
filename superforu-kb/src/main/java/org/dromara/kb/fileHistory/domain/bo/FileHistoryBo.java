package org.dromara.kb.fileHistory.domain.bo;

import org.dromara.kb.fileHistory.domain.FileHistory;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 文件历史业务对象 kb_file_history
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FileHistory.class, reverseConvertGenerate = false)
public class FileHistoryBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { AddGroup.class })
    private Long id;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkUserId;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkUserName;

    /**
     * 文件ID
     */
    @NotNull(message = "文件ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkFileId;

    /**
     * 文件内容
     */
    @NotBlank(message = "文件内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fileContent;

    /**
     * 提交备注
     */
    @NotBlank(message = "提交备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mark;

    /**
     * 图片地址
     */
    @NotBlank(message = "图片地址不能为空", groups = { AddGroup.class })
    private String picUrl;


}
