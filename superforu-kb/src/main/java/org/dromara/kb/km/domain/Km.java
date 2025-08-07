package org.dromara.kb.km.domain;

import jakarta.validation.constraints.NotNull;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 知识库对象 kb_km
 *
 * @author zzg
 * @date 2023-12-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_km")
public class Km extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 知识库名称
     */
    private String name;

    /**
     * 介绍
     */
    private String mark;

    /**
     * 最大磁盘空间(B)
     */
    private Long maxSpace;

    /**
     * 已使用磁盘空间(B)
     */
    private Long usedSpace;

    /**
     * 集成项目ID
     */
    private Long outProjectId;

    /**
     * 集成存储ID
     */
    private Long outStorageId;

    /**
     * 图片地址
     */
    private String picUrl;


    /**
     * 删除标志
     */
    @TableLogic
    private Long delFlag;

    /**
     * 类型
     */
    private String type;

    /**
     * 用户账号
     */
    private String linkUserAccount;

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
