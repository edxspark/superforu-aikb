package org.dromara.com.user.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户信息对象 com_user
 *
 * @author Lion Li
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("com_user")
public class ComUser extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 名称
     */
    private String userName;

    /**
     * 密码
     */
    private String userPsw;

    /**
     * 签名
     */
    private String signature;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * zh-CN：中文，en-US：英文
     */
    private String language;

    /**
     * 主题类型（dark: 炫黑，light: 亮白）
     */
    private String theme;

    /**
     * 删除标志(0：未删除，2：已删除)
     */
    @TableLogic
    private Integer delFlag;


}
