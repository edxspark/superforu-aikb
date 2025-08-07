package org.dromara.kb.fileShare.domain.bo;

import org.dromara.kb.fileShare.domain.FileShare;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 文件分享业务对象 kb_file_share
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FileShare.class, reverseConvertGenerate = false)
public class FileShareBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkUserId;

    /**
     * 用户名称
     */
    private String linkUserName;

    /**
     * 文件ID
     */
    private Long linkFileId;

    /**
     * 文件内容ID
     */
    private Long linkFileContentId;

    /**
     * 分享ID
     */
    @NotBlank(message = "分享ID不能为空", groups = { AddGroup.class })
    private String shareCode;

    /**
     * 图片地址
     */
    private String picUrl;


}
