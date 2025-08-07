import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { FileContentVO, FileContentForm, FileContentQuery } from '@/api/kb/fileContent/types';

/**
 * 查询文件内容列表
 * @param query
 * @returns {*}
 */

export const listFileContent = (query?: FileContentQuery): AxiosPromise<FileContentVO[]> => {
  return request({
    url: '/kb/fileContent/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询文件内容详细
 * @param id
 */
export const getFileContent = (id: string | number): AxiosPromise<FileContentVO> => {
  return request({
    url: '/kb/fileContent/' + id,
    method: 'get'
  });
};

/**
 * 新增文件内容
 * @param data
 */
export const addFileContent = (data: FileContentForm) => {
  return request({
    url: '/kb/fileContent',
    method: 'post',
    data: data
  });
};

/**
 * 修改文件内容
 * @param data
 */
export const updateFileContent = (data: FileContentForm) => {
  return request({
    url: '/kb/fileContent',
    method: 'put',
    data: data
  });
};

/**
 * 删除文件内容
 * @param id
 */
export const delFileContent = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/fileContent/' + id,
    method: 'delete'
  });
};
