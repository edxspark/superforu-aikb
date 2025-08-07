import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { KmVO, KmForm, KmQuery } from '@/api/kb/km/types';

/**
 * 查询知识库列表
 * @param query
 * @returns {*}
 */

export const listKm = (query?: KmQuery): AxiosPromise<KmVO[]> => {
  return request({
    url: '/kb/km/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询知识库详细
 * @param id
 */
export const getKm = (id: string | number): AxiosPromise<KmVO> => {
  return request({
    url: '/kb/km/' + id,
    method: 'get'
  });
};

/**
 * 新增知识库
 * @param data
 */
export const addKm = (data: KmForm) => {
  return request({
    url: '/kb/km',
    method: 'post',
    data: data
  });
};

/**
 * 修改知识库
 * @param data
 */
export const updateKm = (data: KmForm) => {
  return request({
    url: '/kb/km',
    method: 'put',
    data: data
  });
};

/**
 * 删除知识库
 * @param id
 */
export const delKm = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/km/' + id,
    method: 'delete'
  });
};
