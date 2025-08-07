import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { AnalysisRecordVO, AnalysisRecordForm, AnalysisRecordQuery } from '@/api/voice/analysisRecord/types';

/**
 * 查询语音分析记录列表
 * @param query
 * @returns {*}
 */

export const listAnalysisRecord = (query?: AnalysisRecordQuery): AxiosPromise<AnalysisRecordVO[]> => {
  return request({
    url: '/voice/analysisRecord/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询语音分析记录详细
 * @param id
 */
export const getAnalysisRecord = (id: string | number): AxiosPromise<AnalysisRecordVO> => {
  return request({
    url: '/voice/analysisRecord/' + id,
    method: 'get'
  });
};

/**
 * 新增语音分析记录
 * @param data
 */
export const addAnalysisRecord = (data: AnalysisRecordForm) => {
  return request({
    url: '/voice/analysisRecord',
    method: 'post',
    data: data
  });
};

/**
 * 修改语音分析记录
 * @param data
 */
export const updateAnalysisRecord = (data: AnalysisRecordForm) => {
  return request({
    url: '/voice/analysisRecord',
    method: 'put',
    data: data
  });
};

/**
 * 删除语音分析记录
 * @param id
 */
export const delAnalysisRecord = (id: string | number | Array<string | number>) => {
  return request({
    url: '/voice/analysisRecord/' + id,
    method: 'delete'
  });
};
