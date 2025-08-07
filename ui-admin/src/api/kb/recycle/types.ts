export interface RecycleVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 删除对象类型
   */
  type: string;

  /**
   * 删除对象ID
   */
  linkId: string | number;

  /**
   * 删除对象名称
   */
  linkName: string;

}

export interface RecycleForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 删除对象类型
   */
  type?: string;

  /**
   * 删除对象ID
   */
  linkId?: string | number;

  /**
   * 删除对象名称
   */
  linkName?: string;

  /**
   * 彻底删除时间
   */
  completelyDelTime?: string;

}

export interface RecycleQuery extends PageQuery {

  /**
   * 删除对象类型
   */
  type?: string;

  /**
   * 删除对象ID
   */
  linkId?: string | number;

  /**
   * 删除对象名称
   */
  linkName?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



