package org.dromara.com.menuConfig.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.com.menuConfig.domain.bo.ComMenuConfigBo;
import org.dromara.com.menuConfig.domain.vo.ComMenuConfigVo;
import org.dromara.com.menuConfig.domain.ComMenuConfig;
import org.dromara.com.menuConfig.mapper.ComMenuConfigMapper;
import org.dromara.com.menuConfig.service.IComMenuConfigService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 菜单配置Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class ComMenuConfigServiceImpl implements IComMenuConfigService {

    private final ComMenuConfigMapper baseMapper;

    /**
     * 查询菜单配置
     */
    @Override
    public ComMenuConfigVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询菜单配置列表
     */
    @Override
    public TableDataInfo<ComMenuConfigVo> queryPageList(ComMenuConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ComMenuConfig> lqw = buildQueryWrapper(bo);
        Page<ComMenuConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询菜单配置列表
     */
    @Override
    public List<ComMenuConfigVo> queryList(ComMenuConfigBo bo) {
        LambdaQueryWrapper<ComMenuConfig> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ComMenuConfig> buildQueryWrapper(ComMenuConfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ComMenuConfig> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), ComMenuConfig::getName, bo.getName());
        lqw.eq(bo.getSort() != null, ComMenuConfig::getSort, bo.getSort());
        lqw.eq(bo.getStatus() != null, ComMenuConfig::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增菜单配置
     */
    @Override
    public Boolean insertByBo(ComMenuConfigBo bo) {
        ComMenuConfig add = MapstructUtils.convert(bo, ComMenuConfig.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改菜单配置
     */
    @Override
    public Boolean updateByBo(ComMenuConfigBo bo) {
        ComMenuConfig update = MapstructUtils.convert(bo, ComMenuConfig.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ComMenuConfig entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除菜单配置
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
