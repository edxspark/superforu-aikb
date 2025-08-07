import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { PayOrderVO, PayOrderForm, PayOrderQuery } from '@/api/kb/payOrder/types';

/**
 * 查询支付订单列表
 * @param query
 * @returns {*}
 */

export const listPayOrder = (query?: PayOrderQuery): AxiosPromise<PayOrderVO[]> => {
  return request({
    url: '/kb/payOrder/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询支付订单详细
 * @param id
 */
export const getPayOrder = (id: string | number): AxiosPromise<PayOrderVO> => {
  return request({
    url: '/kb/payOrder/' + id,
    method: 'get'
  });
};

/**
 * 新增支付订单
 * @param data
 */
export const addPayOrder = (data: PayOrderForm) => {
  return request({
    url: '/kb/payOrder',
    method: 'post',
    data: data
  });
};

/**
 * 修改支付订单
 * @param data
 */
export const updatePayOrder = (data: PayOrderForm) => {
  return request({
    url: '/kb/payOrder',
    method: 'put',
    data: data
  });
};

/**
 * 删除支付订单
 * @param id
 */
export const delPayOrder = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/payOrder/' + id,
    method: 'delete'
  });
};
