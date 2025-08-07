package org.dromara.com.userPackagePromotion.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 优惠码对象 com_user_package_promotion
 *
 * @author Lion Li
 * @date 2024-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("com_user_package_promotion")
public class ComUserPackagePromotion extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 优惠码
     */
    private String promotionCode;

    /**
     * 优惠金额
     */
    private Integer promotionValue;

    /**
     * 最大使用次数
     */
    private Integer maxUseCount;

    /**
     * 已经使用次数
     */
    private Integer usedCount;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 删除标志(0：未删除，2：已删除)
     */
    @TableLogic
    private Long delFlag;


}
