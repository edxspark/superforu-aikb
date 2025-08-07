package org.dromara.com.userConfig.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.com.superModuleConfig.domain.ComSuperModuleConfig;
import org.dromara.com.superModuleConfig.domain.vo.ComSuperModuleConfigVo;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 用户超级模块配置视图对象 com_super_module_config
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComSuperModuleConfig.class)
public class ComUserSuperModuleConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 配置类型
     */
    @ExcelProperty(value = "配置类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "com_user_config")
    private Integer type;

    private List<ComSuperModuleConfigVo> userSuperModuleConfigList;



}
