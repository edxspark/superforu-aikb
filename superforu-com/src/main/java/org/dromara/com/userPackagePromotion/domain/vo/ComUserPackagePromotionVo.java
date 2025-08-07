package org.dromara.com.userPackagePromotion.domain.vo;

import org.dromara.com.userPackagePromotion.domain.ComUserPackagePromotion;
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
 * 优惠码视图对象 com_user_package_promotion
 *
 * @author Lion Li
 * @date 2024-04-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUserPackagePromotion.class)
public class ComUserPackagePromotionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 优惠码
     */
    @ExcelProperty(value = "优惠码")
    private String promotionCode;

    /**
     * 优惠金额
     */
    @ExcelProperty(value = "优惠金额")
    private Integer promotionValue;

    /**
     * 最大使用次数
     */
    @ExcelProperty(value = "最大使用次数")
    private Integer maxUseCount;

    /**
     * 已经使用次数
     */
    @ExcelProperty(value = "已经使用次数")
    private Integer usedCount;


}
