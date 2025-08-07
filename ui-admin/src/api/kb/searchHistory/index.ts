import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { SearchHistoryVO, SearchHistoryForm, SearchHistoryQuery } from '@/api/kb/searchHistory/types';

/**
 * 查询搜索历史列表
 * @param query
 * @returns {*}
 */

export const listSearchHistory = (query?: SearchHistoryQuery): AxiosPromise<SearchHistoryVO[]> => {
  return request({
    url: '/kb/searchHistory/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询搜索历史详细
 * @param id
 */
export const getSearchHistory = (id: string | number): AxiosPromise<SearchHistoryVO> => {
  return request({
    url: '/kb/searchHistory/' + id,
    method: 'get'
  });
};

/**
 * 新增搜索历史
 * @param data
 */
export const addSearchHistory = (data: SearchHistoryForm) => {
  return request({
    url: '/kb/searchHistory',
    method: 'post',
    data: data
  });
};

/**
 * 修改搜索历史
 * @param data
 */
export const updateSearchHistory = (data: SearchHistoryForm) => {
  return request({
    url: '/kb/searchHistory',
    method: 'put',
    data: data
  });
};

/**
 * 删除搜索历史
 * @param id
 */
export const delSearchHistory = (id: string | number | Array<string | number>) => {
  return request({
    url: '/kb/searchHistory/' + id,
    method: 'delete'
  });
};
