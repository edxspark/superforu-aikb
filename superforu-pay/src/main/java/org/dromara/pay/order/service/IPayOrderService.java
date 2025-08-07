package org.dromara.pay.order.service;

import org.dromara.pay.order.domain.PayOrder;
import org.dromara.pay.order.domain.vo.PayOrderVo;
import org.dromara.pay.order.domain.bo.PayOrderBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 支付订单Service接口
 *
 * @author Lion Li
 * @date 2024-03-22
 */
public interface IPayOrderService {

    /**
     * 查询支付订单
     */
    PayOrderVo queryById(Long id);

    /**
     * 查询支付订单列表
     */
    TableDataInfo<PayOrderVo> queryPageList(PayOrderBo bo, PageQuery pageQuery);

    /**
     * 查询支付订单列表
     */
    List<PayOrderVo> queryList(PayOrderBo bo);

    /**
     * 新增支付订单
     */
    Boolean insertByBo(PayOrderBo bo);

    /**
     * 修改支付订单
     */
    Boolean updateByBo(PayOrderBo bo);

    /**
     * 校验并批量删除支付订单信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
