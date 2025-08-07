package org.dromara.com.superModuleConfig.domain.bo;

import org.dromara.com.superModuleConfig.domain.ComSuperModuleConfig;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 超级模块配置新增业务对象 com_super_module_config
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComSuperModuleConfig.class, reverseConvertGenerate = false)
public class ComSuperModuleConfigBo extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sort;

    /**
     * 用户等级id关联
     */
    private Long linkUserEquityId;

    /**
     * 是否初始化（0：初始化 1:未初始化）
     */
    @NotNull(message = "是否初始化（0：初始化 1:未初始化）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 配置内容
     */
    @NotBlank(message = "配置内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String value;

    /**
     * 模块ICON
     */
    @NotBlank(message = "模块ICON不能为空", groups = { AddGroup.class, EditGroup.class })
    private String icon;

    /**
     * 模块颜色
     */
    @NotBlank(message = "模块颜色不能为空", groups = { AddGroup.class, EditGroup.class })
    private String color;

    /**
     * 打开方式
     */
    private String openWay;


}
