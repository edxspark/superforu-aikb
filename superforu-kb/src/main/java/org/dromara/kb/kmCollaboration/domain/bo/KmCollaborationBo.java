package org.dromara.kb.kmCollaboration.domain.bo;

import org.dromara.kb.kmCollaboration.domain.KmCollaboration;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 协同管理业务对象 kb_km_collaboration
 *
 * @author zzg
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = KmCollaboration.class, reverseConvertGenerate = false)
public class KmCollaborationBo extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 知识库ID
     */
    @NotNull(message = "知识库ID不能为空", groups = { AddGroup.class })
    private Long linkKmId;

    /**
     * 团队ID
     */
    @NotNull(message = "团队ID不能为空", groups = { AddGroup.class })
    private Long linkTeamId;

    /**
     * 状态：0：使用中 1：已删除
     * */
    private int status;

    /**
     * 回收站ID
     */
    private Long linkCycleId;


}
