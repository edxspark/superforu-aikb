package org.dromara.kb.fileContent.domain.bo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.dromara.kb.fileContent.domain.FileContent;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 文件内容业务对象 kb_file_content
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FileContent.class, reverseConvertGenerate = false)
public class FileContentBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 文件ID
     */
    private Long linkFileId;


    /**
     * 文件内容
     */
    private String linkFileContent;

    /**
     * 状态：0：使用中 1：已删除
     * */
    private int status;

}
