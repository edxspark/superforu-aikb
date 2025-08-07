package org.dromara.kb.fileContent.mapper;

import org.dromara.kb.fileContent.domain.FileContent;
import org.dromara.kb.fileContent.domain.vo.FileContentVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 文件内容Mapper接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface FileContentMapper extends BaseMapperPlus<FileContent, FileContentVo> {
    /**
     * 批量删除文件夹&文件内容
     */
    Integer deleteByFolderId(String catalogIds,Long cycleId);
}
