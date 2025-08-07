package org.dromara.com.userPackagePurchase.domain.bo;

import org.dromara.com.userPackagePurchase.domain.ComUserPackagePurchase;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户套餐购买详细业务对象 com_user_package_purchase
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComUserPackagePurchase.class, reverseConvertGenerate = false)
public class ComUserPackagePurchaseBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkUserId;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageCode;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageName;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unit;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float price;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer number;

    /**
     * 总价
     */
    @NotNull(message = "总价不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float total;

    /**
     * 支付渠道
     */
    private String payWay;


    /**
     * 备注
     */
    private String remark;

    /**
     * 优惠码
     */
    private String promotionCode;

    /**
     * 优惠金额
     */
    private Integer promotionValue;


}
