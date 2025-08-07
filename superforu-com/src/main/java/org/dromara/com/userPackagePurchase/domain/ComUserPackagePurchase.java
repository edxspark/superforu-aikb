package org.dromara.com.userPackagePurchase.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户套餐购买详细对象 com_user_package_purchase
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("com_user_package_purchase")
public class ComUserPackagePurchase extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户ID
     */
    private Long linkUserId;

    /**
     * 编码
     */
    private String packageCode;

    /**
     * 名称
     */
    private String packageName;

    /**
     * 换算
     */
    private Integer convertUtil;

    /**
     * 单位
     */
    private String unit;


    /**
     * 价格
     */
    private Float price;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 总价
     */
    private Float total;


    /**
     * 支付单号
     */
    private String payNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 删除标志(0：未删除，2：已删除)
     */
    @TableLogic
    private Long delFlag;

    /**
     * 优惠码
     */
    private String promotionCode;

    /**
     * 优惠金额
     */
    private Integer promotionValue;

}
