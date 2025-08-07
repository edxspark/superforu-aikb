package org.dromara.com.teamMate.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.com.user.domain.ComUser;

import java.io.Serial;
import java.io.Serializable;


/**
 * 未在该团队的用户信息视图对象 com_user
 *
 * @author JackLiao
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUser.class)
public class ComUserNotInTeamInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 账号
     */
    @ExcelProperty(value = "账号")
    private String userAccount;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String userName;


}
