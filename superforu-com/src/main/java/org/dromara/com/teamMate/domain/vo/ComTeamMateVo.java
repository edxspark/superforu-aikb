package org.dromara.com.teamMate.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.dromara.com.teamMate.domain.ComTeamMate;
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
 * 团队成员管理视图对象 com_team_mate
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComTeamMate.class)
public class ComTeamMateVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 角色类型（0：查看者，1：编辑者，2：管理员）
     */
    @ExcelProperty(value = "角色类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "com_role_type")
    private Integer roleType;

    /**
     * 团队id
     */
    @ExcelProperty(value = "团队id")
    private Long linkTeamId;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long linkUserId;

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

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


}
