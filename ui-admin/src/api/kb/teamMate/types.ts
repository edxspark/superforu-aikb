export interface TeamMateVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 角色类型
   */
  roleType: number;

  /**
   * 团队id
   */
  linkTeamId: string | number;

  /**
   * 用户id
   */
  linkUserId: string | number;

}

export interface TeamMateForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 角色类型
   */
  roleType?: number;

  /**
   * 团队id
   */
  linkTeamId?: string | number;

  /**
   * 用户id
   */
  linkUserId?: string | number;

}

export interface TeamMateQuery extends PageQuery {

  /**
   * 角色类型
   */
  roleType?: number;

  /**
   * 团队id
   */
  linkTeamId?: string | number;

  /**
   * 用户id
   */
  linkUserId?: string | number;

    /**
     * 日期范围参数
     */
    params?: any;
}



