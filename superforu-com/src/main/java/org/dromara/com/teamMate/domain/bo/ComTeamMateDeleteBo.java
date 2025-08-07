package org.dromara.com.teamMate.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.com.teamMate.domain.ComTeamMate;
import org.dromara.common.core.validate.EditGroup;

/**
 * 团队成员删除管理业务对象 com_team_mate
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data
@AutoMapper(target = ComTeamMate.class, reverseConvertGenerate = false)
public class ComTeamMateDeleteBo {



    /**
     * 团队id
     */
    @NotNull(message = "团队ID不能为空", groups = { EditGroup.class })
    private Long teamId;

    /**
     * 团队成员id
     */
    @NotNull(message = "团队成员ID不能为空", groups = { EditGroup.class })
    private Long teamMateId;



}
