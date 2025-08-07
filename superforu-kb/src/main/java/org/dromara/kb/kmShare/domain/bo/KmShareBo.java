package org.dromara.kb.kmShare.domain.bo;

import org.dromara.kb.kmShare.domain.KmShare;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 分享预览业务对象 kb_km_share
 *
 * @author zzg
 * @date 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = KmShare.class, reverseConvertGenerate = false)
public class KmShareBo extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 分享ID
     */
    private String shareId;

    /**
     * 知识库ID
     */
    @NotNull(message = "知识库ID不能为空", groups = { AddGroup.class })
    private Long linkKmId;

    /**
     * 访问权限
     */
    @NotBlank(message = "访问权限不能为空", groups = { AddGroup.class })
    private String accessPermission;

    /**
     * 访问权密码
     */
    private String accessPassword;

    /**
     * 状态：0：使用中 1：已删除
     * */
    private int status;

    /**
     * 回收站ID
     */
    private Long linkCycleId;

}
