package org.dromara.kb.folderFile.service;

import org.dromara.common.core.domain.R;
import org.dromara.kb.folderFile.domain.FolderFile;
import org.dromara.kb.folderFile.domain.bo.FolderFileRenameBo;
import org.dromara.kb.folderFile.domain.bo.FolderFileTemplateBo;
import org.dromara.kb.folderFile.domain.vo.FolderFileVo;
import org.dromara.kb.folderFile.domain.bo.FolderFileBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文件夹&文件Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IFolderFileService {

    /**
     * 查询文件夹&文件
     */
    FolderFileVo queryById(Long id);

    /**
     * 查询分享文件夹&文件
     */
    FolderFileVo queryShareFolderFileByShareId(String shareId,Long fileId);

    /**
     * 查询文件夹&文件列表
     */
    TableDataInfo<FolderFileVo> queryPageList(FolderFileBo bo, PageQuery pageQuery);

    /**
     * 查询文件夹&文件列表
     */
    TableDataInfo<FolderFileVo> queryPageRecentlyEdit(FolderFileBo bo, PageQuery pageQuery);


    /**
     * 查询文件夹&文件列表
     */
    List<FolderFileVo> queryList(FolderFileBo bo);

    /**
     * 新增文件夹&文件
     */
    R<Long> insertByBo(FolderFileBo bo);

    /**
     * 使用模版创建文件
     */
    R<FolderFileVo> insertByTemplateBo(FolderFileTemplateBo bo);
    /**
     * 修改文件夹&文件
     */
    Boolean updateByBo(FolderFileRenameBo bo);

    /**
     * 校验并批量删除文件夹&文件信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isCreateCycle, Long cycleId);

    /**
     * ES文档查询
     */
    String fileESSearch(String key);

}
