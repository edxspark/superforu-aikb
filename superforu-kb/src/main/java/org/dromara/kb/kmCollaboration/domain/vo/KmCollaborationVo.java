package org.dromara.kb.kmCollaboration.domain.vo;

import org.dromara.kb.kmCollaboration.domain.KmCollaboration;
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
 * 协同管理视图对象 kb_km_collaboration
 *
 * @author zzg
 * @date 2023-12-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KmCollaboration.class)
public class KmCollaborationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 知识库ID
     */
    @ExcelProperty(value = "知识库ID")
    private Long linkKmId;

    /**
     * 团队ID
     */
    @ExcelProperty(value = "团队ID")
    private Long linkTeamId;

    /**
     * 团队名称
     */
    @ExcelProperty(value = "团队名称")
    private String linkTeamName;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private Long createBy;


}
