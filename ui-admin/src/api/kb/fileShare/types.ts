export interface FileShareVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 用户ID
   */
  linkUserId: string | number;

  /**
   * 用户名称
   */
  linkUserName: string;

  /**
   * 文件ID
   */
  linkFileId: string | number;

  /**
   * 文件内容ID
   */
  linkFileContentId: string | number;

  /**
   * 分享ID
   */
  shareCode: string;

}

export interface FileShareForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 用户ID
   */
  linkUserId?: string | number;

  /**
   * 用户名称
   */
  linkUserName?: string;

  /**
   * 文件ID
   */
  linkFileId?: string | number;

  /**
   * 文件内容ID
   */
  linkFileContentId?: string | number;

  /**
   * 分享ID
   */
  shareCode?: string;

  /**
   * 图片地址
   */
  picUrl?: string;

}

export interface FileShareQuery extends PageQuery {

  /**
   * 用户ID
   */
  linkUserId?: string | number;

  /**
   * 文件ID
   */
  linkFileId?: string | number;

  /**
   * 分享ID
   */
  shareCode?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



