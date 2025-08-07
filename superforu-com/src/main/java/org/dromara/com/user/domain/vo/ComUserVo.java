package org.dromara.com.user.domain.vo;

import org.dromara.com.user.domain.ComUser;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.com.userPackageDetail.domain.vo.ComUserPackageDetailVo;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 用户信息视图对象 com_user
 *
 * @author JackLiao
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUser.class)
public class ComUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 账号
     */
    @ExcelProperty(value = "账号")
    private String userAccount;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String userName;

    /**
     * 签名
     */
    @ExcelProperty(value = "签名")
    private String signature;

    /**
     * 图片地址
     */
    @ExcelProperty(value = "图片地址")
    private String picUrl;

    /**
     * 主题类型（dark: 炫黑，light: 亮白）
     */
    @ExcelProperty(value = "主题类型（dark: 炫黑，light: 亮白）")
    private String theme;

    /**
     * 语言类型(zh-CN：中文，en-US：英文)
     */
    @ExcelProperty(value = "语言类型(zh-CN：中文，en-US：英文)")
    private String language;

    /**
     * 用户权益包详细
     * */
    private List<ComUserPackageDetailVo> userPackageDetail;

}
