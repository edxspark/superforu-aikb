package org.dromara.kb.kmShare.domain.vo;

import org.dromara.kb.kmShare.domain.KmShare;
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
 * 分享预览视图对象 kb_km_share
 *
 * @author zzg
 * @date 2023-12-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KmShare.class)
public class KmShareVo implements Serializable {

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
     * 分享ID
     */
    private String shareId;

    /**
     * 分享链接
     */
    private String shareURL;

    /**
     * 编辑器URL
     */
    private String editorURL;

    /**
     * 访问权限
     */
    private String accessPermission;

    /**
     * 访问权密码
     */
    private String accessPassword;

}
