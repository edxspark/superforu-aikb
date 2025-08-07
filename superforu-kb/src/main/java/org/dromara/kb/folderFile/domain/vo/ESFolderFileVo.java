package org.dromara.kb.folderFile.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import org.dromara.kb.folderFile.domain.FolderFile;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * ES查询VO
 *
 * @author Moks
 * @date 2024-02-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolderFile.class)
public class ESFolderFileVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 类型ICON
     */
    private String icon;

    /**
     * 类型颜色
     */
    private String color;

    /**
     * 文件内容
     */
    private String fileContent;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建者
     */
    private String lastUpdateTime;

    /**
     * 文件类型code
     * */
    private String linkFileTypeCode;

    /**
     * 文件类型名称
     * */
    private String linkFileTypeName;
}
