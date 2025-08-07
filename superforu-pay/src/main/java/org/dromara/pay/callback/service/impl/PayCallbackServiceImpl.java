package org.dromara.pay.callback.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.pay.callback.domain.bo.PayCallbackBo;
import org.dromara.pay.callback.domain.vo.PayCallbackVo;
import org.dromara.pay.callback.domain.PayCallback;
import org.dromara.pay.callback.mapper.PayCallbackMapper;
import org.dromara.pay.callback.service.IPayCallbackService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 支付回调Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-22
 */
@RequiredArgsConstructor
@Service
public class PayCallbackServiceImpl implements IPayCallbackService {

    private final PayCallbackMapper baseMapper;

    /**
     * 查询支付回调
     */
    @Override
    public PayCallbackVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询支付回调列表
     */
    @Override
    public TableDataInfo<PayCallbackVo> queryPageList(PayCallbackBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PayCallback> lqw = buildQueryWrapper(bo);
        Page<PayCallbackVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询支付回调列表
     */
    @Override
    public List<PayCallbackVo> queryList(PayCallbackBo bo) {
        LambdaQueryWrapper<PayCallback> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PayCallback> buildQueryWrapper(PayCallbackBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<PayCallback> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getOrderNo() != null, PayCallback::getOrderNo, bo.getOrderNo());
        lqw.eq(bo.getStatus() != null, PayCallback::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增支付回调
     */
    @Override
    public Boolean insertByBo(PayCallbackBo bo) {
        PayCallback add = MapstructUtils.convert(bo, PayCallback.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改支付回调
     */
    @Override
    public Boolean updateByBo(PayCallbackBo bo) {
        PayCallback update = MapstructUtils.convert(bo, PayCallback.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(PayCallback entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除支付回调
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
