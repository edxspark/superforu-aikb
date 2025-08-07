package org.dromara.kb.storageConfig.domain.bo;

import org.dromara.kb.storageConfig.domain.StorageConfig;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 集成存储配置业务对象 kb_storage_config
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageConfig.class, reverseConvertGenerate = false)
public class StorageConfigBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 团队成员ID
     */
    @NotNull(message = "团队成员ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkTeamId;

    /**
     * 配置信息JSON
     */
    @NotBlank(message = "配置信息JSON不能为空", groups = { AddGroup.class, EditGroup.class })
    private String configJson;

    /**
     * 图片地址
     */
    private String picUrl;


}
