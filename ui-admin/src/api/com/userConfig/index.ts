import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { UserConfigVO, UserConfigForm, UserConfigQuery } from '@/api/com/userConfig/types';

/**
 * 查询用户配置列表
 * @param query
 * @returns {*}
 */

export const listUserConfig = (query?: UserConfigQuery): AxiosPromise<UserConfigVO[]> => {
  return request({
    url: '/com/userConfig/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询用户配置详细
 * @param id
 */
export const getUserConfig = (id: string | number): AxiosPromise<UserConfigVO> => {
  return request({
    url: '/com/userConfig/' + id,
    method: 'get'
  });
};

/**
 * 新增用户配置
 * @param data
 */
export const addUserConfig = (data: UserConfigForm) => {
  return request({
    url: '/com/userConfig',
    method: 'post',
    data: data
  });
};

/**
 * 修改用户配置
 * @param data
 */
export const updateUserConfig = (data: UserConfigForm) => {
  return request({
    url: '/com/userConfig',
    method: 'put',
    data: data
  });
};

/**
 * 删除用户配置
 * @param id
 */
export const delUserConfig = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/userConfig/' + id,
    method: 'delete'
  });
};
