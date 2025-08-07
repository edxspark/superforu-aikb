package org.dromara.com.userPackageUseDetail.domain.bo;

import org.dromara.com.userPackageUseDetail.domain.ComUserPackageUseDetail;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户充值消费明细业务对象 com_user_package_use_detail
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComUserPackageUseDetail.class, reverseConvertGenerate = false)
public class ComUserPackageUseDetailBo extends BaseEntity {

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
     * 本次消费数量
     */
    @NotNull(message = "本次消费数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float number;

    /**
     * 消费前结余
     */
    @NotNull(message = "消费前结余不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float balanceBefore;

    /**
     * 消费后结余
     */
    @NotNull(message = "消费后结余不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float balanceAfter;


}
