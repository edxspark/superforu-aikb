import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { UserPackagePromotionVO, UserPackagePromotionForm, UserPackagePromotionQuery } from '@/api/com/userPackagePromotion/types';

/**
 * 查询优惠码列表
 * @param query
 * @returns {*}
 */

export const listUserPackagePromotion = (query?: UserPackagePromotionQuery): AxiosPromise<UserPackagePromotionVO[]> => {
  return request({
    url: '/com/userPackagePromotion/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询优惠码详细
 * @param id
 */
export const getUserPackagePromotion = (id: string | number): AxiosPromise<UserPackagePromotionVO> => {
  return request({
    url: '/com/userPackagePromotion/' + id,
    method: 'get'
  });
};

/**
 * 新增优惠码
 * @param data
 */
export const addUserPackagePromotion = (data: UserPackagePromotionForm) => {
  return request({
    url: '/com/userPackagePromotion',
    method: 'post',
    data: data
  });
};

/**
 * 修改优惠码
 * @param data
 */
export const updateUserPackagePromotion = (data: UserPackagePromotionForm) => {
  return request({
    url: '/com/userPackagePromotion',
    method: 'put',
    data: data
  });
};

/**
 * 删除优惠码
 * @param id
 */
export const delUserPackagePromotion = (id: string | number | Array<string | number>) => {
  return request({
    url: '/com/userPackagePromotion/' + id,
    method: 'delete'
  });
};
