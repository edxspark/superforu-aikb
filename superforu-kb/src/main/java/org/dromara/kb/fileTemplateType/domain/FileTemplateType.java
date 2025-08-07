package org.dromara.kb.fileTemplateType.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文档模板类型对象 kb_file_template_type
 *
 * @author Lion Li
 * @date 2023-12-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_file_template_type")
public class FileTemplateType extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 删除标志
     */
    @TableLogic
    private Long delFlag;


}
