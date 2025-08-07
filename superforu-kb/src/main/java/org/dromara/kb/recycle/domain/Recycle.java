package org.dromara.kb.recycle.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 回收站对象 kb_recycle
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_recycle")
public class Recycle extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 删除对象类型
     */
    private String type;

    /**
     * 删除对象ID
     */
    private Long linkId;

    /**
     * 删除对象名称
     */
    private String linkName;

    /**
     * 彻底删除时间
     */
    private Date completelyDelTime;

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
     * 知识库ID
     */
    private Long linkKmId;

    /**
     * 文件类型ID
     */
    private String linkFileTypeCode;

    /**
     * 文件类型名称
     */
    private String linkFileTypeName;

}
