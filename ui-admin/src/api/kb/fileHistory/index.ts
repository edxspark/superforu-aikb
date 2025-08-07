import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { FileHistoryVO, FileHistoryForm, FileHistoryQuery } from '@/api/kb/fileHistory/types';

/**
 * 查询文件历史列表
 * @param query
 * @returns {*}
 */

export const listFileHistory = (query?: FileHistoryQuery): AxiosPromise<FileHistoryVO[]> => {
  return request({
    url: '/kb/fileHistory/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询文件历史详细
 * @param id
 */
export const getFileHistory = (id: string | number): AxiosPromise<FileHistoryVO> => {
  return request({
    url: '/kb/fileHistory/' + id,
    method: 'get'
  });
};

/**
 * 新增文件历史
 * @param data
 */
export const addFileHistory = (data: FileHistoryForm) => {
  return request({
    url: '/kb/fileHistory',
    method: 'post',
    data: data
  });
};

/**
 * 修改文件历史
 * @param data
 */
export const updateFileHistory = (data: FileHistoryForm) => {
  return request({
    url: '/kb/fileHistory',
    method: 'put',
    data: data
  });
};

/**
 * 删除文件历史
 * @param id
 */
export const delFileHistory = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/fileHistory/' + id,
    method: 'delete'
  });
};
