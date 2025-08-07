package org.dromara.com.team.domain.bo;

import org.dromara.com.team.domain.ComTeam;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 团队管理业务对象 com_team
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComTeam.class, reverseConvertGenerate = false)
public class ComTeamBo extends BaseEntity{


    /**
     * 团队名称
     */
    @NotBlank(message = "团队名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String teamName;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 团队描述
     */
    private String teamDesc;


}
