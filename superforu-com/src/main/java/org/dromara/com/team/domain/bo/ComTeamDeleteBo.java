package org.dromara.com.team.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.com.team.domain.ComTeam;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * 删除团队管理业务对象 com_team
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComTeam.class, reverseConvertGenerate = false)
public class ComTeamDeleteBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "团队Id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;


}
