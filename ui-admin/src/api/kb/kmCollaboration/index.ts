import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { KmCollaborationVO, KmCollaborationForm, KmCollaborationQuery } from '@/api/kb/kmCollaboration/types';

/**
 * 查询协同管理列表
 * @param query
 * @returns {*}
 */

export const listKmCollaboration = (query?: KmCollaborationQuery): AxiosPromise<KmCollaborationVO[]> => {
  return request({
    url: '/kb/kmCollaboration/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询协同管理详细
 * @param id
 */
export const getKmCollaboration = (id: string | number): AxiosPromise<KmCollaborationVO> => {
  return request({
    url: '/kb/kmCollaboration/' + id,
    method: 'get'
  });
};

/**
 * 新增协同管理
 * @param data
 */
export const addKmCollaboration = (data: KmCollaborationForm) => {
  return request({
    url: '/kb/kmCollaboration',
    method: 'post',
    data: data
  });
};

/**
 * 修改协同管理
 * @param data
 */
export const updateKmCollaboration = (data: KmCollaborationForm) => {
  return request({
    url: '/kb/kmCollaboration',
    method: 'put',
    data: data
  });
};

/**
 * 删除协同管理
 * @param id
 */
export const delKmCollaboration = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/kmCollaboration/' + id,
    method: 'delete'
  });
};
