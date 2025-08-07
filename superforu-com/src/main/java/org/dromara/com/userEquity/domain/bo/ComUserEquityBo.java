package org.dromara.com.userEquity.domain.bo;

import org.dromara.com.userEquity.domain.ComUserEquity;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户权益套餐配置业务对象 com_user_equity
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComUserEquity.class, reverseConvertGenerate = false)
public class ComUserEquityBo extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 套餐名称
     */
    @NotBlank(message = "套餐名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 等级
     */
    private Integer level;


}
