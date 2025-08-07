package org.dromara.com.inviteHistory.domain.bo;

import org.dromara.com.inviteHistory.domain.ComInviteHistory;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 邀请记录业务对象 com_invite_history
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComInviteHistory.class, reverseConvertGenerate = false)
public class ComInviteHistoryBo extends BaseEntity {


    /**
     * 邀请者用户id
     */
    @NotNull(message = "邀请者用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkInviterId;

    /**
     * 被邀请者用户id
     */
    @NotNull(message = "被邀请者用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkInviteeId;

    /**
     * 被邀请者用户获得权益
     */
    private String getDetail;


}
