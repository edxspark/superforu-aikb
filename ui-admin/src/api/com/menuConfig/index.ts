import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { MenuConfigVO, MenuConfigForm, MenuConfigQuery } from '@/api/com/menuConfig/types';

/**
 * 查询菜单配置列表
 * @param query
 * @returns {*}
 */

export const listMenuConfig = (query?: MenuConfigQuery): AxiosPromise<MenuConfigVO[]> => {
  return request({
    url: '/com/menuConfig/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询菜单配置详细
 * @param id
 */
export const getMenuConfig = (id: string | number): AxiosPromise<MenuConfigVO> => {
  return request({
    url: '/com/menuConfig/' + id,
    method: 'get'
  });
};

/**
 * 新增菜单配置
 * @param data
 */
export const addMenuConfig = (data: MenuConfigForm) => {
  return request({
    url: '/com/menuConfig',
    method: 'post',
    data: data
  });
};

/**
 * 修改菜单配置
 * @param data
 */
export const updateMenuConfig = (data: MenuConfigForm) => {
  return request({
    url: '/com/menuConfig',
    method: 'put',
    data: data
  });
};

/**
 * 删除菜单配置
 * @param id
 */
export const delMenuConfig = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/menuConfig/' + id,
    method: 'delete'
  });
};
