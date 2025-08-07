package org.dromara.com.quickEntranceConfig.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.com.quickEntranceConfig.domain.ComQuickEntranceConfig;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;


/**
 * 文件类型字典数据配置视图对象
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
public class FileTypeDictDataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类型ICON
     */
    private String icon;

    /**
     * 类型颜色
     */
    private String color;

    /**
     * 后缀名
     */
    private String attrType;

    /**
     * 跳转路径
     */
    private String editorURL;


}
