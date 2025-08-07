package org.dromara.kb.searchHistory.service.impl;

import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;
import org.dromara.kb.searchHistory.domain.bo.SearchHistoryBo;
import org.dromara.kb.searchHistory.domain.vo.SearchHistoryVo;
import org.dromara.kb.searchHistory.domain.SearchHistory;
import org.dromara.kb.searchHistory.mapper.SearchHistoryMapper;
import org.dromara.kb.searchHistory.service.ISearchHistoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 搜索历史Service业务层处理
 *
 * @author Moks
 * @date 2024-03-06
 */
@RequiredArgsConstructor
@Service
public class SearchHistoryServiceImpl implements ISearchHistoryService {

    private final SearchHistoryMapper baseMapper;

    /**
     * 查询搜索历史
     */
    @Override
    public SearchHistoryVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询搜索历史列表
     */
    @Override
    public TableDataInfo<SearchHistoryVo> queryPageList(SearchHistoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SearchHistory> lqw = buildQueryWrapper(bo);
        Page<SearchHistoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询搜索历史列表
     */
    @Override
    public List<SearchHistoryVo> queryList(SearchHistoryBo bo) {
        LambdaQueryWrapper<SearchHistory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SearchHistory> buildQueryWrapper(SearchHistoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SearchHistory> lqw = Wrappers.lambdaQuery();
        LoginUser loginUser = LoginHelper.getLoginUser();

        lqw.eq(StringUtils.isNotBlank(bo.getKeyword()), SearchHistory::getKeyword, bo.getKeyword())
            .eq(true,SearchHistory::getCreateBy,loginUser.getUserId());
        return lqw;
    }

    /**
     * 新增搜索历史
     */
    @Override
    public Boolean insertByBo(SearchHistoryBo bo) {
        SearchHistory add = MapstructUtils.convert(bo, SearchHistory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改搜索历史
     */
    @Override
    public Boolean updateByBo(SearchHistoryBo bo) {
        SearchHistory update = MapstructUtils.convert(bo, SearchHistory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SearchHistory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除搜索历史
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
