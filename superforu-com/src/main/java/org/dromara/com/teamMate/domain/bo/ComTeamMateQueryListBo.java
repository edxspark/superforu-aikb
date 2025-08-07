package org.dromara.com.teamMate.domain.bo;

import com.dtflys.forest.annotation.Query;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.com.teamMate.domain.ComTeamMate;

/**
 * 团队成员查询列表业务对象 com_team_mate
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data
@AutoMapper(target = ComTeamMate.class, reverseConvertGenerate = false)
public class ComTeamMateQueryListBo {


    /**
     * 团队id
     */
    @NotNull(message = "团队id不可为空",groups = Query.class)
    private Long teamId;



}
