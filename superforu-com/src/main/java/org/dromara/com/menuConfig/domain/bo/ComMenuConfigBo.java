package org.dromara.com.menuConfig.domain.bo;

import org.dromara.com.menuConfig.domain.ComMenuConfig;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 菜单配置业务对象 com_menu_config
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComMenuConfig.class, reverseConvertGenerate = false)
public class ComMenuConfigBo extends BaseEntity {

    /**
     * ID
     */
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


}
