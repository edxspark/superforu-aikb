import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { FileTemplateVO, FileTemplateForm, FileTemplateQuery } from '@/api/kb/fileTemplate/types';
import { FileTemplateTypeVO } from "@/api/kb/fileTemplateType/types";


/**
 * 查询文档模板分类列表
 * @param query
 * @returns {*}
 */

export const listFileTemplateType = (query?: FileTemplateQuery): AxiosPromise<FileTemplateTypeVO[]> => {
  return request({
    url: '/kb/fileTemplateType/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询文档模板列表
 * @param query
 * @returns {*}
 */

export const listFileTemplate = (query?: FileTemplateQuery): AxiosPromise<FileTemplateVO[]> => {
  return request({
    url: '/kb/fileTemplate/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询文档模板详细
 * @param id
 */
export const getFileTemplate = (id: string | number): AxiosPromise<FileTemplateVO> => {
  return request({
    url: '/kb/fileTemplate/' + id,
    method: 'get'
  });
};

/**
 * 新增文档模板
 * @param data
 */
export const addFileTemplate = (data: FileTemplateForm) => {
  return request({
    url: '/kb/fileTemplate',
    method: 'post',
    data: data
  });
};

/**
 * 修改文档模板
 * @param data
 */
export const updateFileTemplate = (data: FileTemplateForm) => {
  return request({
    url: '/kb/fileTemplate',
    method: 'put',
    data: data
  });
};

/**
 * 删除文档模板
 * @param id
 */
export const delFileTemplate = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/fileTemplate/' + id,
    method: 'delete'
  });
};
