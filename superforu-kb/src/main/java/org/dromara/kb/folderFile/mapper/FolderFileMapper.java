package org.dromara.kb.folderFile.mapper;

import org.dromara.kb.folderFile.domain.FolderFile;
import org.dromara.kb.folderFile.domain.vo.FolderFileVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 文件夹&文件Mapper接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface FolderFileMapper extends BaseMapperPlus<FolderFile, FolderFileVo> {

    /**
     * 批量删除文件夹&文件信息
     */
    Integer deleteByFolderId(String catalogIds,Long cycleId);

    /**
     * 批量修改文件夹&文件AI状态
     */
    Integer updateFolderFileAiStatus(Integer aiStatus,Long kmId);
}
