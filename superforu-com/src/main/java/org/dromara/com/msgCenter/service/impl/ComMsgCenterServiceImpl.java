package org.dromara.com.msgCenter.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.com.msgCenter.domain.bo.ComMsgCenterBo;
import org.dromara.com.msgCenter.domain.vo.ComMsgCenterVo;
import org.dromara.com.msgCenter.domain.ComMsgCenter;
import org.dromara.com.msgCenter.mapper.ComMsgCenterMapper;
import org.dromara.com.msgCenter.service.IComMsgCenterService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 消息中心Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class ComMsgCenterServiceImpl implements IComMsgCenterService {

    private final ComMsgCenterMapper baseMapper;

    /**
     * 查询消息中心
     */
    @Override
    public ComMsgCenterVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询消息中心列表
     */
    @Override
    public TableDataInfo<ComMsgCenterVo> queryPageList(ComMsgCenterBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ComMsgCenter> lqw = buildQueryWrapper(bo);
        Page<ComMsgCenterVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询消息中心列表
     */
    @Override
    public List<ComMsgCenterVo> queryList(ComMsgCenterBo bo) {
        LambdaQueryWrapper<ComMsgCenter> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ComMsgCenter> buildQueryWrapper(ComMsgCenterBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ComMsgCenter> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getStatus() != null, ComMsgCenter::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增消息中心
     */
    @Override
    public Boolean insertByBo(ComMsgCenterBo bo) {
        ComMsgCenter add = MapstructUtils.convert(bo, ComMsgCenter.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改消息中心
     */
    @Override
    public Boolean updateByBo(ComMsgCenterBo bo) {
        ComMsgCenter update = MapstructUtils.convert(bo, ComMsgCenter.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ComMsgCenter entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除消息中心
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
