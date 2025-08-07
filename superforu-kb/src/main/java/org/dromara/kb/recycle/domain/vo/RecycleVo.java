package org.dromara.kb.recycle.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.kb.recycle.domain.Recycle;
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
 * 回收站视图对象 kb_recycle
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Recycle.class)
public class RecycleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 删除对象类型
     */
    @ExcelProperty(value = "删除对象类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "kb_del_type")
    private String type;

    /**
     * 删除对象ID
     */
    @ExcelProperty(value = "删除对象ID")
    private Long linkId;

    /**
     * 删除对象名称
     */
    @ExcelProperty(value = "删除对象名称")
    private String linkName;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除者
     */
    private String updateBy;

    /**
     * 删除时间
     */
    private Date updateTime;

    /**
     * 知识库ID
     */
    private Long linkKmId;

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
     * 是否是文件夹
     */
    private Long isFolder;

    /**
     * 文件名称
     * */
    private String fileName;

    /**
     * 剩余天数（30天）
     * */
    private String remainderDays;
}
