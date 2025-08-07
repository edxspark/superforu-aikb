package org.dromara.kb.fileTemplate.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文档模板对象 kb_file_template
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_file_template")
public class FileTemplate extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 文件类型
     */
    private String fileTypeCode;

    /**
     * 文件类型名称
     */
    private String fileTypeName;

    /**
     * 引用次数
     */
    private Long useCount;

    /**
     * 状态
     */
    private Long status;

    /**
     * 模板类型ID
     */
    private Long linkFileTemplateTypeId;

    /**
     * 后缀名
     */
    private String attrType;

    /**
     * 内容
     */
    private String attrContent;

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
