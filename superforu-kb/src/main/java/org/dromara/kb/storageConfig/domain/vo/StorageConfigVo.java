package org.dromara.kb.storageConfig.domain.vo;

import org.dromara.kb.storageConfig.domain.StorageConfig;
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
 * 集成存储配置视图对象 kb_storage_config
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageConfig.class)
public class StorageConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String name;

    /**
     * 团队成员ID
     */
    @ExcelProperty(value = "团队成员ID")
    private Long linkTeamId;

    /**
     * 配置信息JSON
     */
    @ExcelProperty(value = "配置信息JSON")
    private String configJson;


}
