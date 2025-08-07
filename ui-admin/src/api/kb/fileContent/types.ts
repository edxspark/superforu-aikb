export interface FileContentVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 文件ID
   */
  linkFileId: string | number;

  /**
   * 知识库ID
   */
  linkKmId: string | number;

  /**
   * 当前文件目录IDS
   */
  catalogIds: string | number;

  /**
   * 文件类型ID
   */
  linkFileCode: string;

}

export interface FileContentForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 文件ID
   */
  linkFileId?: string | number;

  /**
   * 知识库ID
   */
  linkKmId?: string | number;

  /**
   * 当前文件目录IDS
   */
  catalogIds?: string | number;

  /**
   * 文件类型ID
   */
  linkFileCode?: string;

  /**
   * 文件内容
   */
  linkFileContent?: string;

  /**
   * 图片地址
   */
  picUrl?: string;

}

export interface FileContentQuery extends PageQuery {

    /**
     * 日期范围参数
     */
    params?: any;
}



