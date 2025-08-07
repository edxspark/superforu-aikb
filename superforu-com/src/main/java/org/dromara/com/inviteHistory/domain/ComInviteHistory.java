package org.dromara.com.inviteHistory.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 邀请记录对象 com_invite_history
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("com_invite_history")
public class ComInviteHistory extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 邀请者用户id
     */
    private Long linkInviterId;

    /**
     * 被邀请者用户id
     */
    private Long linkInviteeId;

    /**
     * 被邀请者用户获得权益
     */
    private String getDetail;

    /**
     * 删除标志(0：未删除，2：已删除)
     */
    @TableLogic
    private Integer delFlag;


}
