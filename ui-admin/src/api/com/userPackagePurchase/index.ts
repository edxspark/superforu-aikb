import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { UserPackagePurchaseVO, UserPackagePurchaseForm, UserPackagePurchaseQuery } from '@/api/com/userPackagePurchase/types';

/**
 * 查询用户套餐购买详细列表
 * @param query
 * @returns {*}
 */

export const listUserPackagePurchase = (query?: UserPackagePurchaseQuery): AxiosPromise<UserPackagePurchaseVO[]> => {
  return request({
    url: '/com/userPackagePurchase/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询用户套餐购买详细详细
 * @param id
 */
export const getUserPackagePurchase = (id: string | number): AxiosPromise<UserPackagePurchaseVO> => {
  return request({
    url: '/com/userPackagePurchase/' + id,
    method: 'get'
  });
};

/**
 * 新增用户套餐购买详细
 * @param data
 */
export const addUserPackagePurchase = (data: UserPackagePurchaseForm) => {
  return request({
    url: '/com/userPackagePurchase',
    method: 'post',
    data: data
  });
};

/**
 * 修改用户套餐购买详细
 * @param data
 */
export const updateUserPackagePurchase = (data: UserPackagePurchaseForm) => {
  return request({
    url: '/com/userPackagePurchase',
    method: 'put',
    data: data
  });
};

/**
 * 删除用户套餐购买详细
 * @param id
 */
export const delUserPackagePurchase = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/userPackagePurchase/' + id,
    method: 'delete'
  });
};
