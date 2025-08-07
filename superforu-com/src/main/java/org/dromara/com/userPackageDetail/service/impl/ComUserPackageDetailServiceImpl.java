package org.dromara.com.userPackageDetail.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.com.userPackageDetail.domain.bo.ComUserPackageDetailBo;
import org.dromara.com.userPackageDetail.domain.vo.ComUserPackageDetailVo;
import org.dromara.com.userPackageDetail.domain.ComUserPackageDetail;
import org.dromara.com.userPackageDetail.mapper.ComUserPackageDetailMapper;
import org.dromara.com.userPackageDetail.service.IComUserPackageDetailService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户权益资源套餐明细Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@RequiredArgsConstructor
@Service
public class ComUserPackageDetailServiceImpl implements IComUserPackageDetailService {

    private final ComUserPackageDetailMapper baseMapper;

    /**
     * 查询用户权益资源套餐明细
     */
    @Override
    public ComUserPackageDetailVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询用户权益资源套餐明细列表
     */
    @Override
    public TableDataInfo<ComUserPackageDetailVo> queryPageList(ComUserPackageDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ComUserPackageDetail> lqw = buildQueryWrapper(bo);
        Page<ComUserPackageDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户权益资源套餐明细列表
     */
    @Override
    public List<ComUserPackageDetailVo> queryList(ComUserPackageDetailBo bo) {
        LambdaQueryWrapper<ComUserPackageDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ComUserPackageDetail> buildQueryWrapper(ComUserPackageDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ComUserPackageDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getLinkUserId() != null, ComUserPackageDetail::getLinkUserId, bo.getLinkUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getPackageCode()), ComUserPackageDetail::getPackageCode, bo.getPackageCode());
        lqw.like(StringUtils.isNotBlank(bo.getPackageName()), ComUserPackageDetail::getPackageName, bo.getPackageName());
        return lqw;
    }

    /**
     * 新增用户权益资源套餐明细
     */
    @Override
    public Boolean insertByBo(ComUserPackageDetailBo bo) {
        ComUserPackageDetail add = MapstructUtils.convert(bo, ComUserPackageDetail.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户权益资源套餐明细
     */
    @Override
    public Boolean updateByBo(ComUserPackageDetailBo bo) {
        ComUserPackageDetail update = MapstructUtils.convert(bo, ComUserPackageDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ComUserPackageDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户权益资源套餐明细
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
