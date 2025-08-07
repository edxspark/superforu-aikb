export interface UserEquityVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 套餐名称
   */
  name: string;

  /**
   * 等级
   */
  level: number;

}

export interface UserEquityForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 套餐名称
   */
  name?: string;

  /**
   * 等级
   */
  level?: number;

}

export interface UserEquityQuery extends PageQuery {

  /**
   * 套餐名称
   */
  name?: string;

  /**
   * 等级
   */
  level?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



