export interface MsgCenterVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 用户ID
   */
  userId: string | number;

  /**
   * 用户名称
   */
  userName: string;

  /**
   * 状态
   */
  status: number;

}

export interface MsgCenterForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 类型
   */
  type?: string;

  /**
   * 用户ID
   */
  userId?: string | number;

  /**
   * 用户名称
   */
  userName?: string;

  /**
   * 状态
   */
  status?: number;

}

export interface MsgCenterQuery extends PageQuery {

  /**
   * 状态
   */
  status?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



