package org.dromara.com.userEquity.domain.vo;

import org.dromara.com.userEquity.domain.ComUserEquity;
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
 * 用户权益套餐配置视图对象 com_user_equity
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUserEquity.class)
public class ComUserEquityVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 套餐名称
     */
    @ExcelProperty(value = "套餐名称")
    private String name;

    /**
     * 等级
     */
    @ExcelProperty(value = "等级", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "com_level")
    private Integer level;


}
