package org.dromara.kb.kmShare.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 分享预览对象 kb_km_share
 *
 * @author zzg
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_km_share")
public class KmShare extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 分享ID
     */
    private String shareId;

    /**
     * 知识库ID
     */
    private Long linkKmId;


    /**
     * 访问权限
     */
    private String accessPermission;

    /**
     * 访问权限值
     */
    private String accessValues;

    /**
     * 访问权密码
     */
    private String accessPassword;

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
