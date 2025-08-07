package org.dromara.kb.fileHistory.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文件历史对象 kb_file_history
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_file_history")
public class FileHistory extends TenantEntity {

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
     * 文件内容
     */
    private String fileContent;

    /**
     * 提交备注
     */
    private String mark;

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
