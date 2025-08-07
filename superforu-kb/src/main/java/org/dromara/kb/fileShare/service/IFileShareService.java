package org.dromara.kb.fileShare.service;

import org.dromara.kb.fileShare.domain.FileShare;
import org.dromara.kb.fileShare.domain.vo.FileShareVo;
import org.dromara.kb.fileShare.domain.bo.FileShareBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文件分享Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IFileShareService {

    /**
     * 查询文件分享
     */
    FileShareVo queryById(Long id);

    /**
     * 查询文件分享列表
     */
    TableDataInfo<FileShareVo> queryPageList(FileShareBo bo, PageQuery pageQuery);

    /**
     * 查询文件分享列表
     */
    List<FileShareVo> queryList(FileShareBo bo);

    /**
     * 新增文件分享
     */
    Boolean insertByBo(FileShareBo bo);

    /**
     * 修改文件分享
     */
    Boolean updateByBo(FileShareBo bo);

    /**
     * 校验并批量删除文件分享信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
