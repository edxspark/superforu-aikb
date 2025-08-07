import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { MsgCenterVO, MsgCenterForm, MsgCenterQuery } from '@/api/com/msgCenter/types';

/**
 * 查询消息中心列表
 * @param query
 * @returns {*}
 */

export const listMsgCenter = (query?: MsgCenterQuery): AxiosPromise<MsgCenterVO[]> => {
  return request({
    url: '/com/msgCenter/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询消息中心详细
 * @param id
 */
export const getMsgCenter = (id: string | number): AxiosPromise<MsgCenterVO> => {
  return request({
    url: '/com/msgCenter/' + id,
    method: 'get'
  });
};

/**
 * 新增消息中心
 * @param data
 */
export const addMsgCenter = (data: MsgCenterForm) => {
  return request({
    url: '/com/msgCenter',
    method: 'post',
    data: data
  });
};

/**
 * 修改消息中心
 * @param data
 */
export const updateMsgCenter = (data: MsgCenterForm) => {
  return request({
    url: '/com/msgCenter',
    method: 'put',
    data: data
  });
};

/**
 * 删除消息中心
 * @param id
 */
export const delMsgCenter = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/msgCenter/' + id,
    method: 'delete'
  });
};
