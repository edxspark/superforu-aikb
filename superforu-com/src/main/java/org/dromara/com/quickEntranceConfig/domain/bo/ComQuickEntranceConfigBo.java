package org.dromara.com.quickEntranceConfig.domain.bo;

import org.dromara.com.quickEntranceConfig.domain.ComQuickEntranceConfig;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 快捷入口配置业务对象 com_quick_entrance_config
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComQuickEntranceConfig.class, reverseConvertGenerate = false)
public class ComQuickEntranceConfigBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sort;

    /**
     * 文件类型id关联
     */
    @NotNull(message = "文件类型id关联不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkFileTypeId;

    /**
     * 是否初始化（0：初始化 1:未初始化）
     */
    @NotNull(message = "是否初始化（0：初始化 1:未初始化）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;



}
