package org.dromara.com.menuConfig.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 菜单配置对象 com_menu_config
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("com_menu_config")
public class ComMenuConfig extends TenantEntity {

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
     * 状态
     */
    private Long status;

    /**
     * 配置内容
     */
    private String value;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 删除标志(0：未删除，2：已删除)
     */
    @TableLogic
    private Integer delFlag;


}
