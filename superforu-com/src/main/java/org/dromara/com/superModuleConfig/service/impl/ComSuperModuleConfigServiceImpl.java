package org.dromara.com.superModuleConfig.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.dromara.com.superModuleConfig.domain.bo.ComSuperModuleConfigEditBo;
import org.dromara.com.utils.enums.InitializationStatus;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.com.superModuleConfig.domain.bo.ComSuperModuleConfigBo;
import org.dromara.com.superModuleConfig.domain.vo.ComSuperModuleConfigVo;
import org.dromara.com.superModuleConfig.domain.ComSuperModuleConfig;
import org.dromara.com.superModuleConfig.mapper.ComSuperModuleConfigMapper;
import org.dromara.com.superModuleConfig.service.IComSuperModuleConfigService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 超级模块配置Service业务层处理
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@RequiredArgsConstructor
@Service
public class ComSuperModuleConfigServiceImpl implements IComSuperModuleConfigService {

    private final ComSuperModuleConfigMapper baseMapper;

    /**
     * 查询超级模块配置
     */
    @Override
    public ComSuperModuleConfigVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询超级模块配置列表
     */
    @Override
    public TableDataInfo<ComSuperModuleConfigVo> queryPageList(ComSuperModuleConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ComSuperModuleConfig> lqw = buildQueryWrapper(bo);
        Page<ComSuperModuleConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }


    private LambdaQueryWrapper<ComSuperModuleConfig> buildQueryWrapper(ComSuperModuleConfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ComSuperModuleConfig> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), ComSuperModuleConfig::getName, bo.getName());
        lqw.eq(bo.getSort() != null, ComSuperModuleConfig::getSort, bo.getSort());
//        lqw.eq(bo.getLinkUserEquityId() != null, ComSuperModuleConfig::getLinkUserEquityId, bo.getLinkUserEquityId());
        lqw.eq(bo.getStatus() != null, ComSuperModuleConfig::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getValue()), ComSuperModuleConfig::getValue, bo.getValue());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), ComSuperModuleConfig::getIcon, bo.getIcon());
        lqw.eq(StringUtils.isNotBlank(bo.getColor()), ComSuperModuleConfig::getColor, bo.getColor());
        return lqw;
    }

    /**
     * 新增超级模块配置
     */
    @Override
    public Boolean insertByBo(ComSuperModuleConfigBo bo) {
        ComSuperModuleConfig add = MapstructUtils.convert(bo, ComSuperModuleConfig.class);
        validEntityBeforeSave(add);
        return baseMapper.insert(add) > 0;
    }

    /**
     * 修改超级模块配置
     */
    @Override
    public Boolean updateByBo(ComSuperModuleConfigEditBo bo) {
        ComSuperModuleConfig update = MapstructUtils.convert(bo, ComSuperModuleConfig.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 修改超级模块配置
     */
    @Override
    public Boolean updateByBo(ComSuperModuleConfigBo bo) {
        ComSuperModuleConfig update = MapstructUtils.convert(bo, ComSuperModuleConfig.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 查询已初始化的超级模块配置列表
     */
    @Override
    public String queryInitializationList() {
        List<ComSuperModuleConfigVo> comSuperModuleConfigVos = baseMapper.selectVoList(new LambdaQueryWrapper<ComSuperModuleConfig>()
            .eq(ComSuperModuleConfig::getStatus, InitializationStatus.INITIALIZED.getValue()));
        return comSuperModuleConfigVos.stream()
            .map(vo -> String.valueOf(vo.getId()))
            .collect(Collectors.joining(","));    }

    @Override
    public List<ComSuperModuleConfigVo> queryUserSuperModuleConfigList(List<String> ids) {
        return baseMapper.selectVoList(new LambdaQueryWrapper<ComSuperModuleConfig>()
            .in(CollUtil.isNotEmpty(ids),ComSuperModuleConfig::getId,ids));
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ComSuperModuleConfig entity){
        //TODO 做一些数据校验,如唯一约束
    }
}
