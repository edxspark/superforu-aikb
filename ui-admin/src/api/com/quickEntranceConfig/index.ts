import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { QuickEntranceConfigVO, QuickEntranceConfigForm, QuickEntranceConfigQuery } from '@/api/com/quickEntranceConfig/types';

/**
 * 查询快捷入口配置列表
 * @param query
 * @returns {*}
 */

export const listQuickEntranceConfig = (query?: QuickEntranceConfigQuery): AxiosPromise<QuickEntranceConfigVO[]> => {
  return request({
    url: '/com/quickEntranceConfig/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询快捷入口配置详细
 * @param id
 */
export const getQuickEntranceConfig = (id: string | number): AxiosPromise<QuickEntranceConfigVO> => {
  return request({
    url: '/com/quickEntranceConfig/' + id,
    method: 'get'
  });
};

/**
 * 新增快捷入口配置
 * @param data
 */
export const addQuickEntranceConfig = (data: QuickEntranceConfigForm) => {
  return request({
    url: '/com/quickEntranceConfig',
    method: 'post',
    data: data
  });
};

/**
 * 修改快捷入口配置
 * @param data
 */
export const updateQuickEntranceConfig = (data: QuickEntranceConfigForm) => {
  return request({
    url: '/com/quickEntranceConfig',
    method: 'put',
    data: data
  });
};

/**
 * 删除快捷入口配置
 * @param id
 */
export const delQuickEntranceConfig = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/quickEntranceConfig/' + id,
    method: 'delete'
  });
};
