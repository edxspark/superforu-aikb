package org.dromara.kb.searchHistory.service;

import org.dromara.kb.searchHistory.domain.SearchHistory;
import org.dromara.kb.searchHistory.domain.vo.SearchHistoryVo;
import org.dromara.kb.searchHistory.domain.bo.SearchHistoryBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 搜索历史Service接口
 *
 * @author Moks
 * @date 2024-03-06
 */
public interface ISearchHistoryService {

    /**
     * 查询搜索历史
     */
    SearchHistoryVo queryById(Long id);

    /**
     * 查询搜索历史列表
     */
    TableDataInfo<SearchHistoryVo> queryPageList(SearchHistoryBo bo, PageQuery pageQuery);

    /**
     * 查询搜索历史列表
     */
    List<SearchHistoryVo> queryList(SearchHistoryBo bo);

    /**
     * 新增搜索历史
     */
    Boolean insertByBo(SearchHistoryBo bo);

    /**
     * 修改搜索历史
     */
    Boolean updateByBo(SearchHistoryBo bo);

    /**
     * 校验并批量删除搜索历史信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
