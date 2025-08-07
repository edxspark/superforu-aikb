package org.dromara.com.userPackagePurchase.domain.vo;

import org.dromara.com.userPackagePurchase.domain.ComUserPackagePurchase;
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
 * 用户套餐购买详细视图对象 com_user_package_purchase
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUserPackagePurchase.class)
public class ComUserPackagePurchaseVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long linkUserId;

    /**
     * 编码
     */
    @ExcelProperty(value = "编码", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "com_user_package")
    private String packageCode;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String packageName;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格")
    private Float price;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private Long number;

    /**
     * 总价
     */
    @ExcelProperty(value = "总价")
    private Long total;

    /**
     * 支付单号
     */
    @ExcelProperty(value = "支付单号")
    private String payNo;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;



}
