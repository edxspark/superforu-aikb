package org.dromara.com.userEquity.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.com.userEquity.domain.bo.ComUserEquityBo;
import org.dromara.com.userEquity.domain.vo.ComUserEquityVo;
import org.dromara.com.userEquity.domain.ComUserEquity;
import org.dromara.com.userEquity.mapper.ComUserEquityMapper;
import org.dromara.com.userEquity.service.IComUserEquityService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户权益套餐配置Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class ComUserEquityServiceImpl implements IComUserEquityService {

    private final ComUserEquityMapper baseMapper;

    /**
     * 查询用户权益套餐配置
     */
    @Override
    public ComUserEquityVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询用户权益套餐配置列表
     */
    @Override
    public TableDataInfo<ComUserEquityVo> queryPageList(ComUserEquityBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ComUserEquity> lqw = buildQueryWrapper(bo);
        Page<ComUserEquityVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户权益套餐配置列表
     */
    @Override
    public List<ComUserEquityVo> queryList(ComUserEquityBo bo) {
        LambdaQueryWrapper<ComUserEquity> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ComUserEquity> buildQueryWrapper(ComUserEquityBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ComUserEquity> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), ComUserEquity::getName, bo.getName());
        lqw.eq(bo.getLevel() != null, ComUserEquity::getLevel, bo.getLevel());
        return lqw;
    }

    /**
     * 新增用户权益套餐配置
     */
    @Override
    public Boolean insertByBo(ComUserEquityBo bo) {
        ComUserEquity add = MapstructUtils.convert(bo, ComUserEquity.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户权益套餐配置
     */
    @Override
    public Boolean updateByBo(ComUserEquityBo bo) {
        ComUserEquity update = MapstructUtils.convert(bo, ComUserEquity.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ComUserEquity entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户权益套餐配置
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
