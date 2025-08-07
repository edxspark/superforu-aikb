package org.dromara.kb.kmCollaboration.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 协同管理对象 kb_km_collaboration
 *
 * @author zzg
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_km_collaboration")
public class KmCollaboration extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 知识库ID
     */
    private Long linkKmId;

    /**
     * 团队ID
     */
    private Long linkTeamId;


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
     * 状态：0：使用中 1：已删除
     * */
    private int status;

    /**
     * 回收站ID
     */
    private Long linkCycleId;


}
