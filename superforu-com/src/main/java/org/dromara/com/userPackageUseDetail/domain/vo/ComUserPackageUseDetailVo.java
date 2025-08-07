package org.dromara.com.userPackageUseDetail.domain.vo;

import org.dromara.com.userPackageUseDetail.domain.ComUserPackageUseDetail;
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
 * 用户充值消费明细视图对象 com_user_package_use_detail
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUserPackageUseDetail.class)
public class ComUserPackageUseDetailVo implements Serializable {

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
    @ExcelProperty(value = "编码")
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
     * 本次消费数量
     */
    @ExcelProperty(value = "本次消费数量")
    private Float number;

    /**
     * 消费前结余
     */
    @ExcelProperty(value = "消费前结余")
    private Float balanceBefore;

    /**
     * 消费后结余
     */
    @ExcelProperty(value = "消费后结余")
    private Float balanceAfter;


}
