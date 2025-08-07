package org.dromara.com.userPackagePromotion.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.com.userPackagePromotion.domain.bo.ComUserPackagePromotionBo;
import org.dromara.com.userPackagePromotion.domain.vo.ComUserPackagePromotionVo;
import org.dromara.com.userPackagePromotion.domain.ComUserPackagePromotion;
import org.dromara.com.userPackagePromotion.mapper.ComUserPackagePromotionMapper;
import org.dromara.com.userPackagePromotion.service.IComUserPackagePromotionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 优惠码Service业务层处理
 *
 * @author Lion Li
 * @date 2024-04-07
 */
@RequiredArgsConstructor
@Service
public class ComUserPackagePromotionServiceImpl implements IComUserPackagePromotionService {

    private final ComUserPackagePromotionMapper baseMapper;

    /**
     * 查询优惠码
     */
    @Override
    public ComUserPackagePromotionVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询优惠码列表
     */
    @Override
    public TableDataInfo<ComUserPackagePromotionVo> queryPageList(ComUserPackagePromotionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ComUserPackagePromotion> lqw = buildQueryWrapper(bo);
        Page<ComUserPackagePromotionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询优惠码列表
     */
    @Override
    public List<ComUserPackagePromotionVo> queryList(ComUserPackagePromotionBo bo) {
        LambdaQueryWrapper<ComUserPackagePromotion> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ComUserPackagePromotion> buildQueryWrapper(ComUserPackagePromotionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ComUserPackagePromotion> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPromotionCode()), ComUserPackagePromotion::getPromotionCode, bo.getPromotionCode());
        return lqw;
    }

    /**
     * 新增优惠码
     */
    @Override
    public Boolean insertByBo(ComUserPackagePromotionBo bo) {
        ComUserPackagePromotion add = MapstructUtils.convert(bo, ComUserPackagePromotion.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改优惠码
     */
    @Override
    public Boolean updateByBo(ComUserPackagePromotionBo bo) {
        ComUserPackagePromotion update = MapstructUtils.convert(bo, ComUserPackagePromotion.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ComUserPackagePromotion entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除优惠码
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
