package org.dromara.com.userConfig.domain.vo;

import org.dromara.com.userConfig.domain.ComUserConfig;
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
 * 用户配置视图对象 com_user_config
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUserConfig.class)
public class ComUserConfigVo implements Serializable {

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
     * 配置类型
     */
    @ExcelProperty(value = "配置类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "com_user_config")
    private Integer type;

    /**
     * 配置内容JSON
     */
    @ExcelProperty(value = "配置内容Json")
    private String configValues;


}
