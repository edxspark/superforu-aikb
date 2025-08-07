export interface InviteHistoryVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 邀请者用户id
   */
  linkInviterId: string | number;

  /**
   * 被邀请者用户id
   */
  linkInviteeId: string | number;

}

export interface InviteHistoryForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 邀请者用户id
   */
  linkInviterId?: string | number;

  /**
   * 被邀请者用户id
   */
  linkInviteeId?: string | number;

}

export interface InviteHistoryQuery extends PageQuery {

  /**
   * 邀请者用户id
   */
  linkInviterId?: string | number;

  /**
   * 被邀请者用户id
   */
  linkInviteeId?: string | number;

    /**
     * 日期范围参数
     */
    params?: any;
}



