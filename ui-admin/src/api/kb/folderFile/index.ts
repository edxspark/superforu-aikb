import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { FolderFileVO, FolderFileForm, FolderFileQuery } from '@/api/kb/folderFile/types';

/**
 * 查询文件夹&文件列表
 * @param query
 * @returns {*}
 */

export const listFolderFile = (query?: FolderFileQuery): AxiosPromise<FolderFileVO[]> => {
  return request({
    url: '/kb/folderFile/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询文件夹&文件详细
 * @param id
 */
export const getFolderFile = (id: string | number): AxiosPromise<FolderFileVO> => {
  return request({
    url: '/kb/folderFile/' + id,
    method: 'get'
  });
};

/**
 * 新增文件夹&文件
 * @param data
 */
export const addFolderFile = (data: FolderFileForm) => {
  return request({
    url: '/kb/folderFile',
    method: 'post',
    data: data
  });
};

/**
 * 修改文件夹&文件
 * @param data
 */
export const updateFolderFile = (data: FolderFileForm) => {
  return request({
    url: '/kb/folderFile',
    method: 'put',
    data: data
  });
};

/**
 * 删除文件夹&文件
 * @param id
 */
export const delFolderFile = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/folderFile/' + id,
    method: 'delete'
  });
};
