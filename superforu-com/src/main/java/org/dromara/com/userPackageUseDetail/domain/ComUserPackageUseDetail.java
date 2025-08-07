package org.dromara.com.userPackageUseDetail.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户充值消费明细对象 com_user_package_use_detail
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("com_user_package_use_detail")
public class ComUserPackageUseDetail extends TenantEntity {

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
     * 换算
     */
    private Integer convertUtil;

    /**
     * 单位
     */
    private String unit;

    /**
     * 本次消费数量
     */
    private Long number;

    /**
     * 消费前结余
     */
    private Long balanceBefore;

    /**
     * 消费后结余
     */
    private Long balanceAfter;

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
