export interface KmShareVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 分享ID
   */
  shareId: string | number;

  /**
   * 团队ID
   */
  outTeamId: string | number;

  /**
   * 访问权限
   */
  accessPermission: string;

  /**
   * 访问权限值
   */
  accessValues: string;

  /**
   * 访问权密码
   */
  accessPassword: string;

}

export interface KmShareForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 分享ID
   */
  shareId?: string | number;

  /**
   * 团队ID
   */
  outTeamId?: string | number;

  /**
   * 访问权限
   */
  accessPermission?: string;

  /**
   * 访问权限值
   */
  accessValues?: string;

  /**
   * 访问权密码
   */
  accessPassword?: string;

}

export interface KmShareQuery extends PageQuery {

  /**
   * 分享ID
   */
  shareId?: string | number;

  /**
   * 团队ID
   */
  outTeamId?: string | number;

  /**
   * 访问权限
   */
  accessPermission?: string;

  /**
   * 访问权限值
   */
  accessValues?: string;

  /**
   * 访问权密码
   */
  accessPassword?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



