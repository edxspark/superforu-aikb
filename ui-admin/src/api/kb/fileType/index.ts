import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { FileTypeVO, FileTypeForm, FileTypeQuery } from '@/api/kb/fileType/types';

/**
 * 查询文件类型列表
 * @param query
 * @returns {*}
 */

export const listFileType = (query?: FileTypeQuery): AxiosPromise<FileTypeVO[]> => {
  return request({
    url: '/kb/fileType/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询文件类型详细
 * @param id
 */
export const getFileType = (id: string | number): AxiosPromise<FileTypeVO> => {
  return request({
    url: '/kb/fileType/' + id,
    method: 'get'
  });
};

/**
 * 新增文件类型
 * @param data
 */
export const addFileType = (data: FileTypeForm) => {
  return request({
    url: '/kb/fileType',
    method: 'post',
    data: data
  });
};

/**
 * 修改文件类型
 * @param data
 */
export const updateFileType = (data: FileTypeForm) => {
  return request({
    url: '/kb/fileType',
    method: 'put',
    data: data
  });
};

/**
 * 删除文件类型
 * @param id
 */
export const delFileType = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/fileType/' + id,
    method: 'delete'
  });
};
