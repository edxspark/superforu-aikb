package org.dromara.com.quickEntranceConfig.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 快捷入口配置对象 com_quick_entrance_config
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("com_quick_entrance_config")
public class ComQuickEntranceConfig extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 文件类型id关联
     */
    private Long linkFileTypeId;

    /**
     * 是否初始化（0：初始化 1:未初始化）
     */
    private Integer status;


    /**
     * 删除标志(0：未删除，2：已删除)
     */
    @TableLogic
    private Integer delFlag;


}
