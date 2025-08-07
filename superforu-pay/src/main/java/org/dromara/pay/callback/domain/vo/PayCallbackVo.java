package org.dromara.pay.callback.domain.vo;

import org.dromara.pay.callback.domain.PayCallback;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 支付回调视图对象 pay_callback
 *
 * @author Lion Li
 * @date 2024-03-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = PayCallback.class)
public class PayCallbackVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 订单编号
     */
    @ExcelProperty(value = "订单编号")
    private Long orderNo;

    /**
     * 通知状态：等待通知、通知成功、通知失败、其他
     */
    @ExcelProperty(value = "通知状态：等待通知、通知成功、通知失败、其他")
    private Long status;

    /**
     * 通知次数
     */
    @ExcelProperty(value = "通知次数")
    private Long count;


}
