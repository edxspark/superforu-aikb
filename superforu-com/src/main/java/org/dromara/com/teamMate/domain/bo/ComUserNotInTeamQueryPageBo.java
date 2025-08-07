package org.dromara.com.teamMate.domain.bo;

import com.dtflys.forest.annotation.Query;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.com.user.domain.ComUser;

/**
 * 查询用户未在团队业务对象 com_team_mate
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data
@AutoMapper(target = ComUser.class, reverseConvertGenerate = false)
public class ComUserNotInTeamQueryPageBo {


    /**
     * 团队id
     */
    @NotNull(message = "团队id不可为空",groups = {Query.class})
    private Long teamId;

    @NotBlank(message = "账号不可为空",groups = {Query.class})
    private String userAccount;


}
