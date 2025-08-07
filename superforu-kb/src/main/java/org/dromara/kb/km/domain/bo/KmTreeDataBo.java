package org.dromara.kb.km.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.kb.km.domain.Km;

/**
 * 知识库业务对象 kb_km
 *
 * @author zzg
 * @date 2023-12-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Km.class, reverseConvertGenerate = false)
public class KmTreeDataBo extends BaseEntity {

    /**
     * ID
     */
    @NotBlank(message = "知识库ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String id;

    /**
     * 目录数据
     */
    private String fileKmTreeData;

}
