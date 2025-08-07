package org.dromara.kb.fileType.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文件类型对象 kb_file_type
 *
 * @author Lion Li
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_file_type")
public class FileType extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型ID
     */
    private String code;

    /**
     * 序号
     */
    private Integer sort;

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
     * 图片地址
     */
    private String picUrl;

    /**
     * 删除标志
     */
    @TableLogic
    private Long delFlag;


}
