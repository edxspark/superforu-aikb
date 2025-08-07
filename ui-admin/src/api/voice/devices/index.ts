import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { DevicesVO, DevicesForm, DevicesQuery } from '@/api/voice/devices/types';

/**
 * 查询电话机设备列表
 * @param query
 * @returns {*}
 */

export const listDevices = (query?: DevicesQuery): AxiosPromise<DevicesVO[]> => {
  return request({
    url: '/voice/devices/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询电话机设备详细
 * @param id
 */
export const getDevices = (id: string | number): AxiosPromise<DevicesVO> => {
  return request({
    url: '/voice/devices/' + id,
    method: 'get'
  });
};

/**
 * 新增电话机设备
 * @param data
 */
export const addDevices = (data: DevicesForm) => {
  return request({
    url: '/voice/devices',
    method: 'post',
    data: data
  });
};

/**
 * 修改电话机设备
 * @param data
 */
export const updateDevices = (data: DevicesForm) => {
  return request({
    url: '/voice/devices',
    method: 'put',
    data: data
  });
};

/**
 * 删除电话机设备
 * @param id
 */
export const delDevices = (id: string | number | Array<string | number>) => {
  return request({
    url: '/voice/devices/' + id,
    method: 'delete'
  });
};
