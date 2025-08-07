package org.dromara.kb.fileShare.domain.vo;

import org.dromara.kb.fileShare.domain.FileShare;
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
 * 文件分享视图对象 kb_file_share
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FileShare.class)
public class FileShareVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long linkUserId;

    /**
     * 用户名称
     */
    @ExcelProperty(value = "用户名称")
    private String linkUserName;

    /**
     * 文件ID
     */
    @ExcelProperty(value = "文件ID")
    private Long linkFileId;

    /**
     * 文件内容ID
     */
    @ExcelProperty(value = "文件内容ID")
    private Long linkFileContentId;

    /**
     * 分享ID
     */
    @ExcelProperty(value = "分享ID")
    private String shareCode;


}
