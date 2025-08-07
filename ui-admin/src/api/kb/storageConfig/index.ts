import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { StorageConfigVO, StorageConfigForm, StorageConfigQuery } from '@/api/kb/storageConfig/types';

/**
 * 查询集成存储配置列表
 * @param query
 * @returns {*}
 */

export const listStorageConfig = (query?: StorageConfigQuery): AxiosPromise<StorageConfigVO[]> => {
  return request({
    url: '/kb/storageConfig/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询集成存储配置详细
 * @param id
 */
export const getStorageConfig = (id: string | number): AxiosPromise<StorageConfigVO> => {
  return request({
    url: '/kb/storageConfig/' + id,
    method: 'get'
  });
};

/**
 * 新增集成存储配置
 * @param data
 */
export const addStorageConfig = (data: StorageConfigForm) => {
  return request({
    url: '/kb/storageConfig',
    method: 'post',
    data: data
  });
};

/**
 * 修改集成存储配置
 * @param data
 */
export const updateStorageConfig = (data: StorageConfigForm) => {
  return request({
    url: '/kb/storageConfig',
    method: 'put',
    data: data
  });
};

/**
 * 删除集成存储配置
 * @param id
 */
export const delStorageConfig = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/storageConfig/' + id,
    method: 'delete'
  });
};
