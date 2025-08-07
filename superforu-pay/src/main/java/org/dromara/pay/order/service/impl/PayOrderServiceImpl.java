package org.dromara.pay.order.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.pay.order.domain.bo.PayOrderBo;
import org.dromara.pay.order.domain.vo.PayOrderVo;
import org.dromara.pay.order.domain.PayOrder;
import org.dromara.pay.order.mapper.PayOrderMapper;
import org.dromara.pay.order.service.IPayOrderService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 支付订单Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-22
 */
@RequiredArgsConstructor
@Service
public class PayOrderServiceImpl implements IPayOrderService {

    private final PayOrderMapper baseMapper;

    /**
     * 查询支付订单
     */
    @Override
    public PayOrderVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询支付订单列表
     */
    @Override
    public TableDataInfo<PayOrderVo> queryPageList(PayOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PayOrder> lqw = buildQueryWrapper(bo);
        Page<PayOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询支付订单列表
     */
    @Override
    public List<PayOrderVo> queryList(PayOrderBo bo) {
        LambdaQueryWrapper<PayOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PayOrder> buildQueryWrapper(PayOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<PayOrder> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getSubject()), PayOrder::getSubject, bo.getSubject());
        lqw.eq(StringUtils.isNotBlank(bo.getPayWayCode()), PayOrder::getPayWayCode, bo.getPayWayCode());
        lqw.eq(bo.getStatus() != null, PayOrder::getStatus, bo.getStatus());
        lqw.orderByDesc(PayOrder::getCreateTime);
        return lqw;
    }

    /**
     * 新增支付订单
     */
    @Override
    public Boolean insertByBo(PayOrderBo bo) {
        PayOrder add = MapstructUtils.convert(bo, PayOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改支付订单
     */
    @Override
    public Boolean updateByBo(PayOrderBo bo) {
        PayOrder update = MapstructUtils.convert(bo, PayOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(PayOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除支付订单
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
