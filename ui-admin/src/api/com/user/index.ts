import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { UserVO, UserForm, UserQuery } from '@/api/com/user/types';

/**
 * 查询用户信息列表
 * @param query
 * @returns {*}
 */

export const listUser = (query?: UserQuery): AxiosPromise<UserVO[]> => {
  return request({
    url: '/com/user/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询用户信息详细
 * @param id
 */
export const getUser = (id: string | number): AxiosPromise<UserVO> => {
  return request({
    url: '/com/user/' + id,
    method: 'get'
  });
};

/**
 * 新增用户信息
 * @param data
 */
export const addUser = (data: UserForm) => {
  return request({
    url: '/com/user',
    method: 'post',
    data: data
  });
};

/**
 * 修改用户信息
 * @param data
 */
export const updateUser = (data: UserForm) => {
  return request({
    url: '/com/user',
    method: 'put',
    data: data
  });
};

/**
 * 删除用户信息
 * @param id
 */
export const delUser = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/user/' + id,
    method: 'delete'
  });
};
