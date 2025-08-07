import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { UserEquityVO, UserEquityForm, UserEquityQuery } from '@/api/com/userEquity/types';

/**
 * 查询用户权益套餐配置列表
 * @param query
 * @returns {*}
 */

export const listUserEquity = (query?: UserEquityQuery): AxiosPromise<UserEquityVO[]> => {
  return request({
    url: '/com/userEquity/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询用户权益套餐配置详细
 * @param id
 */
export const getUserEquity = (id: string | number): AxiosPromise<UserEquityVO> => {
  return request({
    url: '/com/userEquity/' + id,
    method: 'get'
  });
};

/**
 * 新增用户权益套餐配置
 * @param data
 */
export const addUserEquity = (data: UserEquityForm) => {
  return request({
    url: '/com/userEquity',
    method: 'post',
    data: data
  });
};

/**
 * 修改用户权益套餐配置
 * @param data
 */
export const updateUserEquity = (data: UserEquityForm) => {
  return request({
    url: '/com/userEquity',
    method: 'put',
    data: data
  });
};

/**
 * 删除用户权益套餐配置
 * @param id
 */
export const delUserEquity = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/userEquity/' + id,
    method: 'delete'
  });
};
