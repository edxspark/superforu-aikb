package org.dromara.com.msgCenter.domain.bo;

import org.dromara.com.msgCenter.domain.ComMsgCenter;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 消息中心业务对象 com_msg_center
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComMsgCenter.class, reverseConvertGenerate = false)
public class ComMsgCenterBo extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 状态
     */
    private Long status;


}
