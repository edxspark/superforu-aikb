package org.dromara.kb.storageConfig.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.kb.storageConfig.domain.bo.StorageConfigBo;
import org.dromara.kb.storageConfig.domain.vo.StorageConfigVo;
import org.dromara.kb.storageConfig.domain.StorageConfig;
import org.dromara.kb.storageConfig.mapper.StorageConfigMapper;
import org.dromara.kb.storageConfig.service.IStorageConfigService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 集成存储配置Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class StorageConfigServiceImpl implements IStorageConfigService {

    private final StorageConfigMapper baseMapper;

    /**
     * 查询集成存储配置
     */
    @Override
    public StorageConfigVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询集成存储配置列表
     */
    @Override
    public TableDataInfo<StorageConfigVo> queryPageList(StorageConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<StorageConfig> lqw = buildQueryWrapper(bo);
        Page<StorageConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询集成存储配置列表
     */
    @Override
    public List<StorageConfigVo> queryList(StorageConfigBo bo) {
        LambdaQueryWrapper<StorageConfig> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<StorageConfig> buildQueryWrapper(StorageConfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<StorageConfig> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), StorageConfig::getName, bo.getName());
        lqw.eq(bo.getLinkTeamId() != null, StorageConfig::getLinkTeamId, bo.getLinkTeamId());
        return lqw;
    }

    /**
     * 新增集成存储配置
     */
    @Override
    public Boolean insertByBo(StorageConfigBo bo) {
        StorageConfig add = MapstructUtils.convert(bo, StorageConfig.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改集成存储配置
     */
    @Override
    public Boolean updateByBo(StorageConfigBo bo) {
        StorageConfig update = MapstructUtils.convert(bo, StorageConfig.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(StorageConfig entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除集成存储配置
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
