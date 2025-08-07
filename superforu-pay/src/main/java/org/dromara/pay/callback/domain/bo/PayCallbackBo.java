package org.dromara.pay.callback.domain.bo;

import org.dromara.pay.callback.domain.PayCallback;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 支付回调业务对象 pay_callback
 *
 * @author Lion Li
 * @date 2024-03-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PayCallback.class, reverseConvertGenerate = false)
public class PayCallbackBo extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 订单编号
     */
    @NotNull(message = "订单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderNo;

    /**
     * 通知状态：0:等待通知、1:通知成功、2:通知失败、3:其他
     */
    @NotNull(message = "通知状态：等待通知、通知成功、通知失败、其他不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 通知次数
     */
    @NotNull(message = "通知次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long count;


}
