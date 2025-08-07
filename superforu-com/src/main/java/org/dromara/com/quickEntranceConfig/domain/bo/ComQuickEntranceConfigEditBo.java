package org.dromara.com.quickEntranceConfig.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.com.quickEntranceConfig.domain.ComQuickEntranceConfig;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * 修改快捷入口配置业务对象 com_quick_entrance_config
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComQuickEntranceConfig.class, reverseConvertGenerate = false)
public class ComQuickEntranceConfigEditBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {  EditGroup.class })
    private Long sort;


    /**
     * 是否初始化（0：初始化 1:未初始化）
     */
    @NotNull(message = "是否初始化（0：初始化 1:未初始化）不能为空", groups = {  EditGroup.class })
    private Long status;



}
