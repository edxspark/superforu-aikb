import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { FileShareVO, FileShareForm, FileShareQuery } from '@/api/kb/fileShare/types';

/**
 * 查询文件分享列表
 * @param query
 * @returns {*}
 */

export const listFileShare = (query?: FileShareQuery): AxiosPromise<FileShareVO[]> => {
  return request({
    url: '/kb/fileShare/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询文件分享详细
 * @param id
 */
export const getFileShare = (id: string | number): AxiosPromise<FileShareVO> => {
  return request({
    url: '/kb/fileShare/' + id,
    method: 'get'
  });
};

/**
 * 新增文件分享
 * @param data
 */
export const addFileShare = (data: FileShareForm) => {
  return request({
    url: '/kb/fileShare',
    method: 'post',
    data: data
  });
};

/**
 * 修改文件分享
 * @param data
 */
export const updateFileShare = (data: FileShareForm) => {
  return request({
    url: '/kb/fileShare',
    method: 'put',
    data: data
  });
};

/**
 * 删除文件分享
 * @param id
 */
export const delFileShare = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/fileShare/' + id,
    method: 'delete'
  });
};
