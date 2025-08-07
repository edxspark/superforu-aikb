package org.dromara.kb.km.domain.vo;

import jakarta.validation.constraints.NotBlank;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.kb.km.domain.Km;
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
 * 知识库视图对象 kb_km
 *
 * @author zzg
 * @date 2023-12-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Km.class)
public class KmVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 知识库名称
     */
    @ExcelProperty(value = "知识库名称")
    private String name;


    /**
     * 介绍
     */
    @ExcelProperty(value = "介绍")
    private String mark;

    /**
     * 最大磁盘空间
     */
    @ExcelProperty(value = "最大磁盘空间")
    private String maxSpace;

    /**
     * 已使用磁盘空间
     */
    @ExcelProperty(value = "已使用磁盘空间")
    private String usedSpace;

    /**
     * 百分比
     */
    @ExcelProperty(value = "百分比")
    private String percent;

    /**
     * 类型
     */
    private String type;

    /**
     * 用户账号
     */
    private String linkUserAccount;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 图片OSS地址
     */
    private String picOSSUrl;

    /**
     * 所属类型 自己：myself, 分享者：share
     */
    private String ownerType;

    /**
     * 知识文档树数据
     */
    private String fileKmTreeData;

    /**
     * 是否开启AI
     * 0:关闭
     * 1:开启
     * */
    private int isAiOpen;

    /**
     * 智能体类型
     * */
    @ExcelDictFormat(dictType = "agent_type")
    private String agentType;
}


