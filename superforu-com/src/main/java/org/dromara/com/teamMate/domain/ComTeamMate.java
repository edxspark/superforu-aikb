package org.dromara.com.teamMate.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 团队成员管理对象 com_team_mate
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("com_team_mate")
public class ComTeamMate extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 角色类型（0：查看者，1：编辑者，2：管理员）
     */
    private Integer roleType;

    /**
     * 团队id
     */
    private Long linkTeamId;

    /**
     * 用户id
     */
    private Long linkUserId;

    /**
     * 删除标志(0：未删除，2：已删除)
     */
    @TableLogic
    private Integer delFlag;


}
