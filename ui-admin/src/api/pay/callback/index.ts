import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { CallbackVO, CallbackForm, CallbackQuery } from '@/api/pay/callback/types';

/**
 * 查询支付回调列表
 * @param query
 * @returns {*}
 */

export const listCallback = (query?: CallbackQuery): AxiosPromise<CallbackVO[]> => {
  return request({
    url: '/pay/callback/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询支付回调详细
 * @param id
 */
export const getCallback = (id: string | number): AxiosPromise<CallbackVO> => {
  return request({
    url: '/pay/callback/' + id,
    method: 'get'
  });
};

/**
 * 新增支付回调
 * @param data
 */
export const addCallback = (data: CallbackForm) => {
  return request({
    url: '/pay/callback',
    method: 'post',
    data: data
  });
};

/**
 * 修改支付回调
 * @param data
 */
export const updateCallback = (data: CallbackForm) => {
  return request({
    url: '/pay/callback',
    method: 'put',
    data: data
  });
};

/**
 * 删除支付回调
 * @param id
 */
export const delCallback = (id: string | number | Array<string | number>) => {
  return request({
    url: '/pay/callback/' + id,
    method: 'delete'
  });
};
