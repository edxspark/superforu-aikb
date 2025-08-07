package org.dromara.kb.fileHistory.service;

import org.dromara.kb.fileHistory.domain.FileHistory;
import org.dromara.kb.fileHistory.domain.vo.FileHistoryVo;
import org.dromara.kb.fileHistory.domain.bo.FileHistoryBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文件历史Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IFileHistoryService {

    /**
     * 查询文件历史
     */
    FileHistoryVo queryById(Long id);

    /**
     * 查询文件历史列表
     */
    TableDataInfo<FileHistoryVo> queryPageList(FileHistoryBo bo, PageQuery pageQuery);

    /**
     * 查询文件历史列表
     */
    List<FileHistoryVo> queryList(FileHistoryBo bo);

    /**
     * 新增文件历史
     */
    Boolean insertByBo(FileHistoryBo bo);

    /**
     * 修改文件历史
     */
    Boolean updateByBo(FileHistoryBo bo);

    /**
     * 校验并批量删除文件历史信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
