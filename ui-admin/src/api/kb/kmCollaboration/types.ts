export interface KmCollaborationVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 团队ID
   */
  outTeamId: string | number;

}

export interface KmCollaborationForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 团队ID
   */
  outTeamId?: string | number;

}

export interface KmCollaborationQuery extends PageQuery {

  /**
   * 团队ID
   */
  outTeamId?: string | number;

    /**
     * 日期范围参数
     */
    params?: any;
}



