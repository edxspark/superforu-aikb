package org.dromara.com.userConfig.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.com.userConfig.domain.ComUserConfig;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * 用户配置业务查询对象 com_user_config
 *
 * @author JackLiao
 * @date 2023-11-28
 */
@Data
@AutoMapper(target = ComUserConfig.class, reverseConvertGenerate = false)
public class ComUserConfigQueryBo {


    /**
     * 用户ID
     */
    private Long linkUserId;

    /**
     * 配置类型
     */
    private Integer type;


}
