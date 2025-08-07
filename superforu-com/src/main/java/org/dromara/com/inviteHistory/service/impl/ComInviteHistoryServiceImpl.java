package org.dromara.com.inviteHistory.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.com.inviteHistory.domain.bo.ComInviteHistoryBo;
import org.dromara.com.inviteHistory.domain.vo.ComInviteHistoryVo;
import org.dromara.com.inviteHistory.domain.ComInviteHistory;
import org.dromara.com.inviteHistory.mapper.ComInviteHistoryMapper;
import org.dromara.com.inviteHistory.service.IComInviteHistoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 邀请记录Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class ComInviteHistoryServiceImpl implements IComInviteHistoryService {

    private final ComInviteHistoryMapper baseMapper;

    /**
     * 查询邀请记录
     */
    @Override
    public ComInviteHistoryVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询邀请记录列表
     */
    @Override
    public TableDataInfo<ComInviteHistoryVo> queryPageList(ComInviteHistoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ComInviteHistory> lqw = buildQueryWrapper(bo);
        Page<ComInviteHistoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询邀请记录列表
     */
    @Override
    public List<ComInviteHistoryVo> queryList(ComInviteHistoryBo bo) {
        LambdaQueryWrapper<ComInviteHistory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ComInviteHistory> buildQueryWrapper(ComInviteHistoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ComInviteHistory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getLinkInviterId() != null, ComInviteHistory::getLinkInviterId, bo.getLinkInviterId());
        lqw.eq(bo.getLinkInviteeId() != null, ComInviteHistory::getLinkInviteeId, bo.getLinkInviteeId());
        return lqw;
    }

    /**
     * 新增邀请记录
     */
    @Override
    public Boolean insertByBo(ComInviteHistoryBo bo) {
        ComInviteHistory add = MapstructUtils.convert(bo, ComInviteHistory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        return flag;
    }

    /**
     * 修改邀请记录
     */
    @Override
    public Boolean updateByBo(ComInviteHistoryBo bo) {
        ComInviteHistory update = MapstructUtils.convert(bo, ComInviteHistory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ComInviteHistory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除邀请记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
