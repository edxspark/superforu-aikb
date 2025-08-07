import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { TeamVO, TeamForm, TeamQuery } from '@/api/kb/team/types';

/**
 * 查询团队管理列表
 * @param query
 * @returns {*}
 */

export const listTeam = (query?: TeamQuery): AxiosPromise<TeamVO[]> => {
  return request({
    url: '/kb/team/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询团队管理详细
 * @param id
 */
export const getTeam = (id: string | number): AxiosPromise<TeamVO> => {
  return request({
    url: '/kb/team/' + id,
    method: 'get'
  });
};

/**
 * 新增团队管理
 * @param data
 */
export const addTeam = (data: TeamForm) => {
  return request({
    url: '/kb/team',
    method: 'post',
    data: data
  });
};

/**
 * 修改团队管理
 * @param data
 */
export const updateTeam = (data: TeamForm) => {
  return request({
    url: '/kb/team',
    method: 'put',
    data: data
  });
};

/**
 * 删除团队管理
 * @param id
 */
export const delTeam = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/team/' + id,
    method: 'delete'
  });
};
