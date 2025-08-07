package org.dromara.com.team.domain.vo;

import org.dromara.com.team.domain.ComTeam;
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
 * 团队管理视图对象 com_team
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComTeam.class)
public class ComTeamVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 团队名称
     */
    @ExcelProperty(value = "团队名称")
    private String teamName;

    /**
     * 团队描述
     */
    @ExcelProperty(value = "团队描述")
    private String teamDesc;

    /**
     * 角色类型（0：查看者，1：编辑者，2：管理员）
     */
    @ExcelProperty(value = "角色类型", converter = ExcelDictConvert.class)
    private Integer roleType;


    /**
     * 图片地址
     */
    @ExcelProperty(value = "图片地址")
    private String picUrl;


}
