import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { SensitiveWordsSettingVO, SensitiveWordsSettingForm, SensitiveWordsSettingQuery } from '@/api/voice/sensitiveWordsSetting/types';

/**
 * 查询敏感词设置列表
 * @param query
 * @returns {*}
 */

export const listSensitiveWordsSetting = (query?: SensitiveWordsSettingQuery): AxiosPromise<SensitiveWordsSettingVO[]> => {
  return request({
    url: '/voice/sensitiveWordsSetting/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询敏感词设置详细
 * @param id
 */
export const getSensitiveWordsSetting = (id: string | number): AxiosPromise<SensitiveWordsSettingVO> => {
  return request({
    url: '/voice/sensitiveWordsSetting/' + id,
    method: 'get'
  });
};

/**
 * 新增敏感词设置
 * @param data
 */
export const addSensitiveWordsSetting = (data: SensitiveWordsSettingForm) => {
  return request({
    url: '/voice/sensitiveWordsSetting',
    method: 'post',
    data: data
  });
};

/**
 * 修改敏感词设置
 * @param data
 */
export const updateSensitiveWordsSetting = (data: SensitiveWordsSettingForm) => {
  return request({
    url: '/voice/sensitiveWordsSetting',
    method: 'put',
    data: data
  });
};

/**
 * 删除敏感词设置
 * @param id
 */
export const delSensitiveWordsSetting = (id: string | number | Array<string | number>) => {
  return request({
    url: '/voice/sensitiveWordsSetting/' + id,
    method: 'delete'
  });
};
