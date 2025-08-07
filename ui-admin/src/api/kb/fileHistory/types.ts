export interface FileHistoryVO {
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
   * 提交备注
   */
  mark: string;

}

export interface FileHistoryForm extends BaseEntity {
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
   * 文件内容
   */
  fileContent?: string;

  /**
   * 提交备注
   */
  mark?: string;

  /**
   * 图片地址
   */
  picUrl?: string;

}

export interface FileHistoryQuery extends PageQuery {

  /**
   * 用户ID
   */
  linkUserId?: string | number;

  /**
   * 用户名称
   */
  linkUserName?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



