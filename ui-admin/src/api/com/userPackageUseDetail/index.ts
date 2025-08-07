import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { UserPackageUseDetailVO, UserPackageUseDetailForm, UserPackageUseDetailQuery } from '@/api/com/userPackageUseDetail/types';

/**
 * 查询用户充值消费明细列表
 * @param query
 * @returns {*}
 */

export const listUserPackageUseDetail = (query?: UserPackageUseDetailQuery): AxiosPromise<UserPackageUseDetailVO[]> => {
  return request({
    url: '/com/userPackageUseDetail/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询用户充值消费明细详细
 * @param id
 */
export const getUserPackageUseDetail = (id: string | number): AxiosPromise<UserPackageUseDetailVO> => {
  return request({
    url: '/com/userPackageUseDetail/' + id,
    method: 'get'
  });
};

/**
 * 新增用户充值消费明细
 * @param data
 */
export const addUserPackageUseDetail = (data: UserPackageUseDetailForm) => {
  return request({
    url: '/com/userPackageUseDetail',
    method: 'post',
    data: data
  });
};

/**
 * 修改用户充值消费明细
 * @param data
 */
export const updateUserPackageUseDetail = (data: UserPackageUseDetailForm) => {
  return request({
    url: '/com/userPackageUseDetail',
    method: 'put',
    data: data
  });
};

/**
 * 删除用户充值消费明细
 * @param id
 */
export const delUserPackageUseDetail = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/userPackageUseDetail/' + id,
    method: 'delete'
  });
};
