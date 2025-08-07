import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { InviteHistoryVO, InviteHistoryForm, InviteHistoryQuery } from '@/api/com/inviteHistory/types';

/**
 * 查询邀请记录列表
 * @param query
 * @returns {*}
 */

export const listInviteHistory = (query?: InviteHistoryQuery): AxiosPromise<InviteHistoryVO[]> => {
  return request({
    url: '/com/inviteHistory/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询邀请记录详细
 * @param id
 */
export const getInviteHistory = (id: string | number): AxiosPromise<InviteHistoryVO> => {
  return request({
    url: '/com/inviteHistory/' + id,
    method: 'get'
  });
};

/**
 * 新增邀请记录
 * @param data
 */
export const addInviteHistory = (data: InviteHistoryForm) => {
  return request({
    url: '/com/inviteHistory',
    method: 'post',
    data: data
  });
};

/**
 * 修改邀请记录
 * @param data
 */
export const updateInviteHistory = (data: InviteHistoryForm) => {
  return request({
    url: '/com/inviteHistory',
    method: 'put',
    data: data
  });
};

/**
 * 删除邀请记录
 * @param id
 */
export const delInviteHistory = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/inviteHistory/' + id,
    method: 'delete'
  });
};
