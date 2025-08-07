package org.dromara.pay.callback.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 支付回调对象 pay_callback
 *
 * @author Lion Li
 * @date 2024-03-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_callback")
public class PayCallback extends TenantEntity {

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
     * 通知状态：0:等待通知、1:通知成功、2:通知失败、3:其他
     */
    private Long status;

    /**
     * 通知次数
     */
    private Long count;

    /**
     * 支付回调参数
     */
    private String rtParams;

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
