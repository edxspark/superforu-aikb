package org.dromara.kb.fileRecently.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 最近编辑对象 kb_file_recently
 *
 * @author Lion Li
 * @date 2023-12-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_file_recently")
public class FileRecently extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 文件ID
     */
    private Long linkFileId;

    /**
     * 用户ID
     */
    private Long linkUserId;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 删除标志
     */
    @TableLogic
    private Long delFlag;

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
