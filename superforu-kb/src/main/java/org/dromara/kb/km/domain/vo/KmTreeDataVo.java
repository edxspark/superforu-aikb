package org.dromara.kb.km.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.kb.km.domain.Km;

import java.io.Serial;
import java.io.Serializable;


/**
 * 知识库视图对象 kb_km
 *
 * @author zzg
 * @date 2023-12-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Km.class)
public class KmTreeDataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 知识库名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;


    /**
     * 知识文档树数据
     */
    private String fileKmTreeData;
}


