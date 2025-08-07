package org.dromara.kb.fileContent.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文件内容对象 kb_file_content
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_file_content")
public class FileContent extends TenantEntity {

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
     * 知识库ID
     */
    private Long linkKmId;

    /**
     * 当前文件目录IDS
     */
    private String catalogIds;

    /**
     * 文件类型ID
     */
    private String linkFileCode;

    /**
     * 文件内容
     */
    private String linkFileContent;

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
     * 回收站ID
     */
    private Long linkCycleId;

    /**
     * 状态：0：使用中 1：已删除
     * */
    private int status;

}
