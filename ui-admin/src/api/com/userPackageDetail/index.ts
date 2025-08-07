import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { UserPackageDetailVO, UserPackageDetailForm, UserPackageDetailQuery } from '@/api/com/userPackageDetail/types';

/**
 * 查询用户权益资源套餐明细列表
 * @param query
 * @returns {*}
 */

export const listUserPackageDetail = (query?: UserPackageDetailQuery): AxiosPromise<UserPackageDetailVO[]> => {
  return request({
    url: '/com/userPackageDetail/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询用户权益资源套餐明细详细
 * @param id
 */
export const getUserPackageDetail = (id: string | number): AxiosPromise<UserPackageDetailVO> => {
  return request({
    url: '/com/userPackageDetail/' + id,
    method: 'get'
  });
};

/**
 * 新增用户权益资源套餐明细
 * @param data
 */
export const addUserPackageDetail = (data: UserPackageDetailForm) => {
  return request({
    url: '/com/userPackageDetail',
    method: 'post',
    data: data
  });
};

/**
 * 修改用户权益资源套餐明细
 * @param data
 */
export const updateUserPackageDetail = (data: UserPackageDetailForm) => {
  return request({
    url: '/com/userPackageDetail',
    method: 'put',
    data: data
  });
};

/**
 * 删除用户权益资源套餐明细
 * @param id
 */
export const delUserPackageDetail = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/userPackageDetail/' + id,
    method: 'delete'
  });
};
