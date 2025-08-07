package org.dromara.com.userPackageDetail.domain.vo;

import org.dromara.com.userPackageDetail.domain.ComUserPackageDetail;
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
 * 用户权益资源套餐明细视图对象 com_user_package_detail
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUserPackageDetail.class)
public class ComUserPackageDetailVo implements Serializable {

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
     * 数量
     */
    @ExcelProperty(value = "数量")
    private Long value;

    /**
     * 应用使用值
     */
    private String appValue;

}
