package org.dromara.pay.order.domain.bo;

import org.dromara.pay.order.domain.PayOrder;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 支付订单业务对象 pay_order
 *
 * @author Lion Li
 * @date 2024-03-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PayOrder.class, reverseConvertGenerate = false)
public class PayOrderBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 商品标题
     */
    @NotBlank(message = "商品标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String subject;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float amount;

    /**
     * 支付渠道编码
     */
    @NotBlank(message = "支付渠道编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String payWayCode;

    /**
     * 支付状态 1：支付成功 0:待支付 2: 支付关闭
     */
    @NotNull(message = "支付状态 1：支付成功 0:待支付 2: 支付关闭不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;


}
