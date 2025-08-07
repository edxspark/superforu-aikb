package org.dromara.kb.fileContent.domain.vo;

import org.dromara.kb.fileContent.domain.FileContent;
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
 * 文件内容视图对象 kb_file_content
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FileContent.class)
public class FileContentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 文件ID
     */
    @ExcelProperty(value = "文件ID")
    private Long linkFileId;

    /**
     * 知识库ID
     */
    @ExcelProperty(value = "知识库ID")
    private Long linkKmId;

    /**
     * 文件类型ID
     */
    @ExcelProperty(value = "文件内容")
    private String linkFileContent;

    /**
     * 回收站ID
     */
    private Long linkCycleId;


}
