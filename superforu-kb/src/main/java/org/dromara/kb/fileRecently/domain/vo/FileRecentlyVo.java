package org.dromara.kb.fileRecently.domain.vo;

import org.dromara.kb.fileRecently.domain.FileRecently;
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
 * 最近编辑视图对象 kb_file_recently
 *
 * @author Lion Li
 * @date 2023-12-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FileRecently.class)
public class FileRecentlyVo implements Serializable {

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
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long linkUserId;


    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件摘要
     */
    private String fileAbstract;

    /**
     * 创建时间
     * */
    private Date createTime;

    /**
     * 编辑时间
     */
    private String editorTime;

    /**
     * 创建人
     * */
    private String createBy;

    /**
     * 修改人
     * */
    private String updateBy;

}
