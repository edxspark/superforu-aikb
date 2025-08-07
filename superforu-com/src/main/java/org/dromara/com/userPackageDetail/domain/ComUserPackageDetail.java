package org.dromara.com.userPackageDetail.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户权益资源套餐明细对象 com_user_package_detail
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("com_user_package_detail")
public class ComUserPackageDetail extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户ID
     */
    private Long linkUserId;

    /**
     * 编码
     */
    private String packageCode;

    /**
     * 名称
     */
    private String packageName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 换算
     */
    private Integer convertUtil;

    /**
     * 数量
     */
    private Long value;

    /**
     * 应用使用值
     */
    private String appValue;



    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 删除标志(0：未删除，2：已删除)
     */
    @TableLogic
    private Long delFlag;


}
