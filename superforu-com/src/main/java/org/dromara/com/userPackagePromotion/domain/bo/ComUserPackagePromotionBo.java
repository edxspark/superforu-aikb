package org.dromara.com.userPackagePromotion.domain.bo;

import org.dromara.com.userPackagePromotion.domain.ComUserPackagePromotion;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 优惠码业务对象 com_user_package_promotion
 *
 * @author Lion Li
 * @date 2024-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComUserPackagePromotion.class, reverseConvertGenerate = false)
public class ComUserPackagePromotionBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 优惠码
     */
    @NotBlank(message = "优惠码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String promotionCode;

    /**
     * 优惠金额
     */
    @NotNull(message = "优惠金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer promotionValue;

    /**
     * 最大使用次数
     */
    @NotNull(message = "最大使用次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer maxUseCount;

    /**
     * 已经使用次数
     */
    @NotNull(message = "已经使用次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer usedCount;


}
