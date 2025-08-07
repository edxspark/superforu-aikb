import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { TeamMateVO, TeamMateForm, TeamMateQuery } from '@/api/com/teamMate/types';

/**
 * 查询团队成员管理列表
 * @param query
 * @returns {*}
 */

export const listTeamMate = (query?: TeamMateQuery): AxiosPromise<TeamMateVO[]> => {
  return request({
    url: '/com/teamMate/list',
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
    url: '/com/teamMate/' + id,
    method: 'get'
  });
};

/**
 * 新增团队成员管理
 * @param data
 */
export const addTeamMate = (data: TeamMateForm) => {
  return request({
    url: '/com/teamMate',
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
    url: '/com/teamMate',
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
    url: '/com/teamMate/' + id,
    method: 'delete'
  });
};
