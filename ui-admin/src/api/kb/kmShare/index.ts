import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { KmShareVO, KmShareForm, KmShareQuery } from '@/api/kb/kmShare/types';

/**
 * 查询分享预览列表
 * @param query
 * @returns {*}
 */

export const listKmShare = (query?: KmShareQuery): AxiosPromise<KmShareVO[]> => {
  return request({
    url: '/kb/kmShare/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询分享预览详细
 * @param id
 */
export const getKmShare = (id: string | number): AxiosPromise<KmShareVO> => {
  return request({
    url: '/kb/kmShare/' + id,
    method: 'get'
  });
};

/**
 * 新增分享预览
 * @param data
 */
export const addKmShare = (data: KmShareForm) => {
  return request({
    url: '/kb/kmShare',
    method: 'post',
    data: data
  });
};

/**
 * 修改分享预览
 * @param data
 */
export const updateKmShare = (data: KmShareForm) => {
  return request({
    url: '/kb/kmShare',
    method: 'put',
    data: data
  });
};

/**
 * 删除分享预览
 * @param id
 */
export const delKmShare = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/kmShare/' + id,
    method: 'delete'
  });
};
