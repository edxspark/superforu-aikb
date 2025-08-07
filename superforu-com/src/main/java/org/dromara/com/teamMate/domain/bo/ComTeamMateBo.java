package org.dromara.com.teamMate.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.com.teamMate.domain.ComTeamMate;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * 团队成员管理业务对象 com_team_mate
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComTeamMate.class, reverseConvertGenerate = false)
public class ComTeamMateBo extends BaseEntity {


    /**
     * 角色类型（0：查看者，1：编辑者，2：管理员）
     */
    @NotNull(message = "角色类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private Integer roleType;

    /**
     * 团队id
     */
    @NotNull(message = "团队ID不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long linkTeamId;

    /**
     * 用户id
     */
    @NotNull(message = "用户ID不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long linkUserId;


}
