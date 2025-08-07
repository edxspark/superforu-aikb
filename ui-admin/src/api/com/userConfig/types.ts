export interface UserConfigVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 用户ID
   */
  linkUserId: string | number;

  /**
   * 配置类型
   */
  type: number;

}

export interface UserConfigForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 用户ID
   */
  linkUserId?: string | number;

  /**
   * 配置类型
   */
  type?: number;

}

export interface UserConfigQuery extends PageQuery {

  /**
   * 用户ID
   */
  linkUserId?: string | number;

  /**
   * 配置类型
   */
  type?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



