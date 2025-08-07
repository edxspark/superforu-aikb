import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { TeamMateVO, TeamMateForm, TeamMateQuery } from '@/api/kb/teamMate/types';

/**
 * 查询团队成员管理列表
 * @param query
 * @returns {*}
 */

export const listTeamMate = (query?: TeamMateQuery): AxiosPromise<TeamMateVO[]> => {
  return request({
    url: '/kb/teamMate/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询团队成员管理详细
 * @param id
 */
export const getTeamMate = (id: string | number): AxiosPromise<TeamMateVO> => {
  return request({
    url: '/kb/teamMate/' + id,
    method: 'get'
  });
};

/**
 * 新增团队成员管理
 * @param data
 */
export const addTeamMate = (data: TeamMateForm) => {
  return request({
    url: '/kb/teamMate',
    method: 'post',
    data: data
  });
};

/**
 * 修改团队成员管理
 * @param data
 */
export const updateTeamMate = (data: TeamMateForm) => {
  return request({
    url: '/kb/teamMate',
    method: 'put',
    data: data
  });
};

/**
 * 删除团队成员管理
 * @param id
 */
export const delTeamMate = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/teamMate/' + id,
    method: 'delete'
  });
};
