package org.dromara.kb.recycle.domain.bo;

import org.dromara.kb.recycle.domain.Recycle;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 回收站业务对象 kb_recycle
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Recycle.class, reverseConvertGenerate = false)
public class RecycleBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 删除对象类型
     */
    private String type;

    /**
     * 删除对象ID
     */
    private Long linkId;

    /**
     * 删除对象名称
     */
    private String linkName;

    /**
     * 彻底删除时间
     */
    private Date completelyDelTime;



    /**
     * 知识库ID
     */
    private Long linkKmId;

    /**
     * 文件类型ID
     */
    private String linkFileTypeCode;

    /**
     * 文件类型名称
     */
    private String linkFileTypeName;


}
