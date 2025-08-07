package org.dromara.pay.common.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.EditGroup;

@Data
public class CreateOrderBo {

    /**
     * 商品标题
     * */
    @NotNull(message = "商品标题", groups = { EditGroup.class })
    private String subject;

    /**
     * 支付渠道编码
     */
    @NotNull(message = "支付渠道编码", groups = { EditGroup.class })
    private String payWayCode;

    /**
     * 交易号
     * */
    @NotNull(message = "交易号", groups = { EditGroup.class })
    private String tradeNo;

    /**
     * 订单总金额
     * */
    @NotNull(message = "订单总金额", groups = { EditGroup.class })
    private String total;


}
