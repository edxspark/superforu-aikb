package org.dromara.kb.km.domain.bo;

import org.dromara.kb.km.domain.Km;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

/**
 * 知识库业务对象 kb_km
 *
 * @author zzg
 * @date 2023-12-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Km.class, reverseConvertGenerate = false)
public class KmBo extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 知识库名称
     */
    @Size(message= "知识库名称不能超过个 {max} 字符",min = 1,max = 8, groups = { AddGroup.class, EditGroup.class})
    @NotBlank(message = "知识库名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 用户账号
     */
    private String linkUserAccount;

    /**
     * 介绍
     */
    private String mark;

    /**
     * 图片地址
     */
    private String picUrl;


    /**
     * 类型
     */
    private String type;


    /**
     * 知识文档树数据
     */
    private String fileKmTreeData;

    /**
     * 回收站关联ID
     * */
    private Long linkCycleId;

    /**
     * 状态：0：使用中 1：已删除
     * */
    private int status;

    /**
     * 是否开启AI
     * 0:关闭
     * 1:开启
     * */
    private int isAiOpen;

    /**
     * 智能体类型
     * */
    private String agentType;

}
