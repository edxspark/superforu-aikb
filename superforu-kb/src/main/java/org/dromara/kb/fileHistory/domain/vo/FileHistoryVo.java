package org.dromara.kb.fileHistory.domain.vo;

import org.dromara.kb.fileHistory.domain.FileHistory;
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
 * 文件历史视图对象 kb_file_history
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FileHistory.class)
public class FileHistoryVo implements Serializable {

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
     * 提交备注
     */
    @ExcelProperty(value = "提交备注")
    private String mark;


}
