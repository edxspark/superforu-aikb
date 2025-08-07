package org.dromara.kb.folderFile.domain;

import jakarta.validation.constraints.NotNull;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文件夹&文件对象 kb_folder_file
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_folder_file")
public class FolderFile extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 父类ID
     */
    private Long parentId;

    /**
     * 当前文件目录IDS
     */
    private String catalogIds;

    /**
     * 是否文件夹
     */
    private Long isFolder;

    /**
     * 用户ID
     */
    private Long linkUserId;

    /**
     * 用户名称
     */
    private String linkUserName;

    /**
     * 知识库ID
     */
    private Long linkKmId;

    /**
     * 编辑状态
     */
    private Integer editing;

    /**
     * 文件类型ID
     */
    private String linkFileTypeCode;

    /**
     * 文件类型名称
     */
    private String linkFileTypeName;

    /**
     * 文件内容ID
     */
    private Long linkFileContentId;

    /**
     * 文件占用空间(B)
     */
    private Long fileSpace;

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
     * 排序
     */
    private Integer sort;

    /**
     * 回收站ID
     */
    private Long linkCycleId;

    /**
     * 知识文档树数据
     */
    private String fileKmTreeData;

    /**
     * 状态：0：使用中 1：已删除
     * */
    private int status;

    /**
     * 状态：0：默认 1：待同步 2: 同步中 3: 已完成 4: 忽略
     * */
    private int aiStatus;

    /**
     * AI同步时间
     */
    private java.util.Date aiSyncTime;

    /**
     * 文件后缀
     */
    private String fileExtension;

}
