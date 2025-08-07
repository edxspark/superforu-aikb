package org.dromara.com.userPackageUseDetail.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.com.userPackageUseDetail.domain.ComUserPackageUseDetail;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * 用户充值消费明细业务对象 com_user_package_use_detail
 *
 * @author Lion Li
 * @date 2024   -03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComUserPackageUseDetail.class, reverseConvertGenerate = false)
public class AddUseDetailBo extends BaseEntity {


    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageCode;


    /**
     * 本次消费数量
     */
    @NotNull(message = "本次消费数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float number;


}
