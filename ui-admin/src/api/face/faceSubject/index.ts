import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { FaceSubjectVO, FaceSubjectForm, FaceSubjectQuery } from '@/api/face/faceSubject/types';

/**
 * 查询人脸识别库列表
 * @param query
 * @returns {*}
 */

export const listFaceSubject = (query?: FaceSubjectQuery): AxiosPromise<FaceSubjectVO[]> => {
  return request({
    url: '/face/faceSubject/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询人脸识别库详细
 * @param id
 */
export const getFaceSubject = (id: string | number): AxiosPromise<FaceSubjectVO> => {
  return request({
    url: '/face/faceSubject/' + id,
    method: 'get'
  });
};

/**
 * 新增人脸识别库
 * @param data
 */
export const addFaceSubject = (data: FaceSubjectForm) => {
  return request({
    url: '/face/faceSubject',
    method: 'post',
    data: data
  });
};

/**
 * 修改人脸识别库
 * @param data
 */
export const updateFaceSubject = (data: FaceSubjectForm) => {
  return request({
    url: '/face/faceSubject',
    method: 'put',
    data: data
  });
};

/**
 * 删除人脸识别库
 * @param id
 */
export const delFaceSubject = (id: string | number | Array<string | number>) => {
  return request({
    url: '/face/faceSubject/' + id,
    method: 'delete'
  });
};
