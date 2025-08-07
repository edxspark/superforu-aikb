import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { OrderVO, OrderForm, OrderQuery } from '@/api/pay/order/types';

/**
 * 查询支付订单列表
 * @param query
 * @returns {*}
 */

export const listOrder = (query?: OrderQuery): AxiosPromise<OrderVO[]> => {
  return request({
    url: '/pay/order/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询支付订单详细
 * @param id
 */
export const getOrder = (id: string | number): AxiosPromise<OrderVO> => {
  return request({
    url: '/pay/order/' + id,
    method: 'get'
  });
};

/**
 * 新增支付订单
 * @param data
 */
export const addOrder = (data: OrderForm) => {
  return request({
    url: '/pay/order',
    method: 'post',
    data: data
  });
};

/**
 * 修改支付订单
 * @param data
 */
export const updateOrder = (data: OrderForm) => {
  return request({
    url: '/pay/order',
    method: 'put',
    data: data
  });
};

/**
 * 删除支付订单
 * @param id
 */
export const delOrder = (id: string | number | Array<string | number>) => {
  return request({
    url: '/pay/order/' + id,
    method: 'delete'
  });
};
