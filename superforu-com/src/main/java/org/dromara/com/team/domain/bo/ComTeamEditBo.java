package org.dromara.com.team.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.com.team.domain.ComTeam;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;

/**
 * 修改团队管理业务对象 com_team
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data

@AutoMapper(target = ComTeam.class, reverseConvertGenerate = false)
public class ComTeamEditBo {

    /**
     * 主键
     */
    @NotNull(message = "团队Id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 团队名称
     */
    @NotBlank(message = "团队名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String teamName;

    /**
     * 图片地址
     */
    @NotBlank(message = "图片地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String picUrl;

    /**
     * 团队描述
     */
    private String teamDesc;


}
