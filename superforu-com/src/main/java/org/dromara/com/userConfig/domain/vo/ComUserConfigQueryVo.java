package org.dromara.com.userConfig.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.com.userConfig.domain.ComUserConfig;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;


/**
 * 用户配置视图对象 com_user_config
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUserConfig.class)
public class ComUserConfigQueryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 用户超级模块配置视图对象
     */
    private ComUserSuperModuleConfigVo comUserSuperModuleConfigVo;


    /**
     * 用户快捷入口配置视图对象
     */
    private ComUserQuickEntranceConfigVo comUserQuickEntranceConfigVo;
}
