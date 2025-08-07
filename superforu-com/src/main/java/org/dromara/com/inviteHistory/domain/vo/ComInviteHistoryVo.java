package org.dromara.com.inviteHistory.domain.vo;

import org.dromara.com.inviteHistory.domain.ComInviteHistory;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 邀请记录视图对象 com_invite_history
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComInviteHistory.class)
public class ComInviteHistoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 邀请者用户id
     */
    @ExcelProperty(value = "邀请者用户id")
    private Long linkInviterId;

    /**
     * 被邀请者用户获得权益
     */
    private String getDetail;

    /**
     * 被邀请者用户id
     */
    @ExcelProperty(value = "被邀请者用户id")
    private Long linkInviteeId;


}
