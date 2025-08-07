package org.dromara.com.superModuleConfig.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 超级模块配置对象 com_super_module_config
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("com_super_module_config")
public class ComSuperModuleConfig extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
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
    private Long sort;

    /**
     * 用户等级id关联
     */
    private Long linkUserEquityId;

    /**
     * 是否初始化（0：初始化 1:未初始化）
     */
    private Long status;

    /**
     * 配置内容
     */
    private String value;

    /**
     * 模块ICON
     */
    private String icon;

    /**
     * 模块颜色
     */
    private String color;

    /**
     * 删除标志(0：未删除，2：已删除)
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 打开方式
     */
    private String openWay;

}
