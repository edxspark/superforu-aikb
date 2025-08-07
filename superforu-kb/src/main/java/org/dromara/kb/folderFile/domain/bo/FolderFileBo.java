package org.dromara.kb.folderFile.domain.bo;

import org.dromara.kb.folderFile.domain.FolderFile;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 文件夹&文件业务对象 kb_folder_file
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolderFile.class, reverseConvertGenerate = false)
public class FolderFileBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long linkUserId;

    /**
     * 文件名称
     */
    @Size(message= "文件名称不能超过个 {max} 字符",min = 1,max = 128, groups = { AddGroup.class, EditGroup.class})
    @NotBlank(message = "文件名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fileName;

    /**
     * 父类ID
     */
    @NotNull(message = "父类ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 是否文件夹
     */
    private Long isFolder;

    /**
     * 知识库ID
     */
    @NotNull(message = "知识库ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkKmId;

    /**
     * 编辑状态
     */
    private Integer editing;

    /**
     * 文件类型ID
     */
    @NotNull(message = "文件类型ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkFileTypeCode;

    /**
     * 文件类型名称
     */
    @NotNull(message = "文件类型名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkFileTypeName;

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
     * 文件内容ID
     */
    private Long linkFileContentId;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 复制文件ID
     */
    private String copyFileId;

    /**
     * 状态：0：待同步 1：已同步
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
