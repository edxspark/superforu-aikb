package org.dromara.com.userConfig.domain.bo;

import org.dromara.com.userConfig.domain.ComUserConfig;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户配置业务对象 com_user_config
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@AutoMapper(target = ComUserConfig.class, reverseConvertGenerate = false)
public class ComUserConfigBo{


    /**
     * 用户ID
     */
    private Long linkUserId;

    /**
     * 配置类型
     */
    private Integer type;


}
