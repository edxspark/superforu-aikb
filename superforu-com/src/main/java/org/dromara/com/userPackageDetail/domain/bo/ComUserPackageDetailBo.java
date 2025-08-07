package org.dromara.com.userPackageDetail.domain.bo;

import org.dromara.com.userPackageDetail.domain.ComUserPackageDetail;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户权益资源套餐明细业务对象 com_user_package_detail
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComUserPackageDetail.class, reverseConvertGenerate = false)
public class ComUserPackageDetailBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkUserId;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageCode;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageName;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unit;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long value;

    /**
     * 应用使用值
     */
    private String appValue;

}
