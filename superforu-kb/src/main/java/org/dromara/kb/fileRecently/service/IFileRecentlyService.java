package org.dromara.kb.fileRecently.service;

import org.dromara.kb.fileRecently.domain.FileRecently;
import org.dromara.kb.fileRecently.domain.vo.FileRecentlyVo;
import org.dromara.kb.fileRecently.domain.bo.FileRecentlyBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 最近编辑Service接口
 *
 * @author Lion Li
 * @date 2023-12-21
 */
public interface IFileRecentlyService {

    /**
     * 查询最近编辑
     */
    FileRecentlyVo queryById(Long id);

    /**
     * 查询最近编辑列表
     */
    TableDataInfo<FileRecentlyVo> queryPageList(FileRecentlyBo bo, PageQuery pageQuery);

    /**
     * 查询最近编辑列表
     */
    List<FileRecentlyVo> queryList(FileRecentlyBo bo);

    /**
     * 新增最近编辑
     */
    Boolean insertByBo(FileRecentlyBo bo);

    /**
     * 修改最近编辑
     */
    Boolean updateByBo(FileRecentlyBo bo);

    /**
     * 校验并批量删除最近编辑信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
