package org.dromara.pay.order.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 支付订单对象 pay_order
 *
 * @author Lion Li
 * @date 2024-03-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_order")
public class PayOrder extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 订单编号
     */
    private Long orderNo;

    /**
     * 商品标题
     */
    private String subject;

    /**
     * 金额
     */
    private Float amount;

    /**
     * 支付渠道编码
     */
    private String payWayCode;

    /**
     * 支付状态 1：支付成功 0:待支付 2: 支付关闭
     */
    private Long status;

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
