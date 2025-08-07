package org.dromara.kb.folderFile.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.dromara.common.tenant.core.TenantEntity;
import org.dromara.kb.folderFile.domain.FolderFile;
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
 * 文件夹&文件视图对象 kb_folder_file
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolderFile.class)
public class FolderFileVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 文件名称
     */
    @ExcelProperty(value = "文件名称")
    private String fileName;

    /**
     * 父类ID
     */
    @ExcelProperty(value = "父类ID")
    private Long parentId;

    /**
     * 当前文件目录IDS
     */
    @ExcelProperty(value = "当前文件目录IDS")
    private String catalogIds;

    /**
     * 是否文件夹
     */
    @ExcelProperty(value = "是否文件夹", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "kb_is_folder")
    private Long isFolder;


    /**
     * 知识库ID
     */
    @ExcelProperty(value = "知识库ID")
    private Long linkKmId;

    /**
     * 编辑状态
     */
    @ExcelProperty(value = "编辑状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "editing")
    private Integer editing;


    /**
     * 知识文档树数据
     */
    private String fileKmTreeData;

    /**
     * 文件类型ID
     */
    private String linkFileTypeCode;

    /**
     * 文件类型名称
     */
    private String linkFileTypeName;

    /**
     * 文件类型ICON
     */
    private String fileIcon;

    /**
     * 文件类型颜色
     */
    private String fileIconColor;

    /**
     * 文件内容ID
     */
    private Long linkFileContentId;

    /**
     * 文件内容
     */
    private String linkFileContent;

    /**
     * 创建者
     */
    private String createBy;


    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 状态：0：默认 1：待同步 2: 同步中 3: 已完成
     * */
    private int aiStatus;

    /**
     * 状态：0：默认 1：待同步 2: 同步中 3: 已完成
     * */
    private String aiStatusLabel;

    /**
     * AI同步时间
     */
    private java.util.Date aiSyncTime;

    /**
     * 文件后缀
     */
    private String fileExtension;

}
