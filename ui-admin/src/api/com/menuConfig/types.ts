export interface MenuConfigVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 名称
   */
  name: string;

  /**
   * 排序
   */
  sort: number;

  /**
   * 状态
   */
  status: number;

}

export interface MenuConfigForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 名称
   */
  name?: string;

  /**
   * 排序
   */
  sort?: number;

  /**
   * 状态
   */
  status?: number;

}

export interface MenuConfigQuery extends PageQuery {

  /**
   * 名称
   */
  name?: string;

  /**
   * 排序
   */
  sort?: number;

  /**
   * 状态
   */
  status?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



