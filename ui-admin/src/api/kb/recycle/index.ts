import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { RecycleVO, RecycleForm, RecycleQuery } from '@/api/kb/recycle/types';

/**
 * 查询回收站列表
 * @param query
 * @returns {*}
 */

export const listRecycle = (query?: RecycleQuery): AxiosPromise<RecycleVO[]> => {
  return request({
    url: '/kb/recycle/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询回收站详细
 * @param id
 */
export const getRecycle = (id: string | number): AxiosPromise<RecycleVO> => {
  return request({
    url: '/kb/recycle/' + id,
    method: 'get'
  });
};

/**
 * 新增回收站
 * @param data
 */
export const addRecycle = (data: RecycleForm) => {
  return request({
    url: '/kb/recycle',
    method: 'post',
    data: data
  });
};

/**
 * 修改回收站
 * @param data
 */
export const updateRecycle = (data: RecycleForm) => {
  return request({
    url: '/kb/recycle',
    method: 'put',
    data: data
  });
};

/**
 * 删除回收站
 * @param id
 */
export const delRecycle = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/recycle/' + id,
    method: 'delete'
  });
};
