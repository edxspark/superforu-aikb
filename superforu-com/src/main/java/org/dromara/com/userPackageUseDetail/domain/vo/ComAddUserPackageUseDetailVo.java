package org.dromara.com.userPackageUseDetail.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.com.userPackageUseDetail.domain.ComUserPackageUseDetail;

import java.io.Serial;
import java.io.Serializable;


/**
 * 用户充值消费明细视图对象 com_user_package_use_detail
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ComUserPackageUseDetail.class)
public class ComAddUserPackageUseDetailVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态:成功：200
     */
    private int status;

    /**
     * 用户ID
     */
    private String msg;

    /**
     * 套餐编码
     */
    private String packageCode;

    /**
     * 剩余额度
     */
    private float balance;

}
