package org.dromara.com.user.domain.bo;

import org.dromara.com.user.domain.ComUser;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户信息业务对象 com_user
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComUser.class, reverseConvertGenerate = false)
public class ComUserBo extends BaseEntity{

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;


    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 签名
     */
    private String signature;

    /**
     * 图片地址
     */
    @NotBlank(message = "图片地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String picUrl;

    /**
     * 语言类型(zh-CN：中文，en-US：英文)
     */
    @NotNull(message = "zh-CN：中文，en-US：英文 不能为空", groups = { AddGroup.class, EditGroup.class })
    private String language;

    /**
     * 主题类型（dark: 炫黑，light: 亮白）
     */
    @NotNull(message = "主题类型（dark: 炫黑，light: 亮白）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String theme;

}
