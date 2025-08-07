package org.dromara.com.userPackagePurchase.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.com.userPackagePurchase.domain.ComUserPackagePurchase;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * 用户套餐购买详细业务对象 com_user_package_purchase
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComUserPackagePurchase.class, reverseConvertGenerate = false)
public class ComUserPackagePurchaseItemBo extends BaseEntity {

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageCode;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer number;

    /**
     * 支付渠道
     */
    @NotBlank(message = "支付渠道", groups = { AddGroup.class, EditGroup.class })
    private String payWay;

    /**
     * 优惠码
     */
    private String promotion;

    /**
     * 优惠码
     */
    private String promotionCode;

    /**
     * 优惠金额
     */
    private Integer promotionValue;

}
