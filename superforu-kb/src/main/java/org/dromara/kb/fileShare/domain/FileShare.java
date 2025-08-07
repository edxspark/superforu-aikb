package org.dromara.kb.fileShare.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文件分享对象 kb_file_share
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_file_share")
public class FileShare extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户ID
     */
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
    private String shareCode;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 删除标志
     */
    @TableLogic
    private Long delFlag;


}
