package org.dromara.pay.order.domain.vo;

import org.dromara.pay.order.domain.PayOrder;
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
 * 支付订单视图对象 pay_order
 *
 * @author Lion Li
 * @date 2024-03-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = PayOrder.class)
public class PayOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 商品标题
     */
    @ExcelProperty(value = "商品标题")
    private String subject;

    /**
     * 金额
     */
    @ExcelProperty(value = "金额")
    private Float amount;

    /**
     * 支付渠道编码
     */
    @ExcelProperty(value = "支付渠道编码", converter = ExcelDictConvert.class)
    private String payWayCode;

    /**
     * 支付状态 1：支付成功 0:待支付 2: 支付关闭
     */
    @ExcelProperty(value = "支付状态 1：支付成功 0:待支付 2: 支付关闭", converter = ExcelDictConvert.class)
    private Long status;

    private Long orderNo;

    private Date createTime;

}
