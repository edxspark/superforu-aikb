import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { SuperModuleConfigVO, SuperModuleConfigForm, SuperModuleConfigQuery } from '@/api/com/superModuleConfig/types';

/**
 * 查询超级模块配置列表
 * @param query
 * @returns {*}
 */

export const listSuperModuleConfig = (query?: SuperModuleConfigQuery): AxiosPromise<SuperModuleConfigVO[]> => {
  return request({
    url: '/com/superModuleConfig/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询超级模块配置详细
 * @param id
 */
export const getSuperModuleConfig = (id: string | number): AxiosPromise<SuperModuleConfigVO> => {
  return request({
    url: '/com/superModuleConfig/' + id,
    method: 'get'
  });
};

/**
 * 新增超级模块配置
 * @param data
 */
export const addSuperModuleConfig = (data: SuperModuleConfigForm) => {
  return request({
    url: '/com/superModuleConfig',
    method: 'post',
    data: data
  });
};

/**
 * 修改超级模块配置
 * @param data
 */
export const updateSuperModuleConfig = (data: SuperModuleConfigForm) => {
  return request({
    url: '/com/superModuleConfig',
    method: 'put',
    data: data
  });
};

/**
 * 删除超级模块配置
 * @param id
 */
export const delSuperModuleConfig = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/superModuleConfig/' + id,
    method: 'delete'
  });
};
