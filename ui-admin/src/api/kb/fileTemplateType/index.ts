import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { FileTemplateTypeVO, FileTemplateTypeForm, FileTemplateTypeQuery } from '@/api/kb/fileTemplateType/types';

/**
 * 查询文档模板类型列表
 * @param query
 * @returns {*}
 */

export const listFileTemplateType = (query?: FileTemplateTypeQuery): AxiosPromise<FileTemplateTypeVO[]> => {
  return request({
    url: '/kb/fileTemplateType/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询文档模板类型详细
 * @param id
 */
export const getFileTemplateType = (id: string | number): AxiosPromise<FileTemplateTypeVO> => {
  return request({
    url: '/kb/fileTemplateType/' + id,
    method: 'get'
  });
};

/**
 * 新增文档模板类型
 * @param data
 */
export const addFileTemplateType = (data: FileTemplateTypeForm) => {
  return request({
    url: '/kb/fileTemplateType',
    method: 'post',
    data: data
  });
};

/**
 * 修改文档模板类型
 * @param data
 */
export const updateFileTemplateType = (data: FileTemplateTypeForm) => {
  return request({
    url: '/kb/fileTemplateType',
    method: 'put',
    data: data
  });
};

/**
 * 删除文档模板类型
 * @param id
 */
export const delFileTemplateType = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/fileTemplateType/' + id,
    method: 'delete'
  });
};
