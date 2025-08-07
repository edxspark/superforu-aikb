export interface SearchHistoryVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 搜索关键字
   */
  keyword: string;

}

export interface SearchHistoryForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 搜索关键字
   */
  keyword?: string;

}

export interface SearchHistoryQuery extends PageQuery {

  /**
   * 搜索关键字
   */
  keyword?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



