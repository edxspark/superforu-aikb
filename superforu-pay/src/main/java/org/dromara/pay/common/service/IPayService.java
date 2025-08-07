package org.dromara.pay.common.service;

import org.dromara.pay.common.domain.bo.CreateOrderBo;

public interface IPayService {

    /**
     * 创建订单
     * */
    Object createOrder(CreateOrderBo createOrderBo);


}
