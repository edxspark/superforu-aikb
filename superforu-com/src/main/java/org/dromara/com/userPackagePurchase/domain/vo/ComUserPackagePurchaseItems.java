package org.dromara.com.userPackagePurchase.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.com.userPackagePurchase.domain.ComUserPackagePurchase;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;


/**
 * 用户套餐购买详细视图对象 com_user_package_purchase
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUserPackagePurchaseItems.class)
public class ComUserPackagePurchaseItems implements Serializable {



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
     * 折扣
     */
    @ExcelProperty(value = "折扣")
    private String discount;

    /**
     * 最低起售
     */
    @ExcelProperty(value = "最低起售")
    private Integer min;

    /**
     * 描述
     */
    @ExcelProperty(value = "最低起售")
    private String desc;

}
