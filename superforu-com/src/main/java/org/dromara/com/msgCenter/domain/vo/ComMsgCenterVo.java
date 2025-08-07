package org.dromara.com.msgCenter.domain.vo;

import org.dromara.com.msgCenter.domain.ComMsgCenter;
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
 * 消息中心视图对象 com_msg_center
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComMsgCenter.class)
public class ComMsgCenterVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 用户名称
     */
    @ExcelProperty(value = "用户名称")
    private String userName;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "com_sms_status")
    private Long status;


}
