package org.dromara.com.teamMate.domain.bo;

import jakarta.validation.constraints.NotNull;
import org.dromara.com.teamMate.domain.ComTeamMate;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.core.validate.EditGroup;

/**
 * 团队成员管理业务对象 com_team_mate
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data
@AutoMapper(target = ComTeamMate.class, reverseConvertGenerate = false)
public class ComTeamMateEditBo {


    /**
     * 角色类型（0：查看者，1：编辑者，2：管理员）
     */
    @NotNull(message = "角色类型不能为空", groups = { EditGroup.class })
    private Integer roleType;

    /**
     * 团队id
     */
    @NotNull(message = "团队ID不能为空", groups = { EditGroup.class })
    private Long teamId;

    /**
     * 团队成员id
     */
    @NotNull(message = "团队成员ID不能为空", groups = { EditGroup.class })
    private Long id;


}
