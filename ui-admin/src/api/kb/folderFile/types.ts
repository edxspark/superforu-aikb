export interface FolderFileVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 文件名称
   */
  fileName: string;

  /**
   * 父类ID
   */
  parentId: string | number;

  /**
   * 当前文件目录IDS
   */
  catalogIds: string | number;

  /**
   * 是否文件夹
   */
  isFolder: number;

  /**
   * 用户ID
   */
  linkUserId: string | number;

  /**
   * 知识库ID
   */
  linkKmId: string | number;

  /**
   * 编辑状态
   */
  editing: number;

}

export interface FolderFileForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 文件名称
   */
  fileName?: string;

  /**
   * 父类ID
   */
  parentId?: string | number;

  /**
   * 当前文件目录IDS
   */
  catalogIds?: string | number;

  /**
   * 是否文件夹
   */
  isFolder?: number;

  /**
   * 用户ID
   */
  linkUserId?: string | number;

  /**
   * 用户名称
   */
  linkUserName?: string;

  /**
   * 知识库ID
   */
  linkKmId?: string | number;

  /**
   * 编辑状态
   */
  editing?: number;

  /**
   * 文件类型ID
   */
  linkFileTypeCode?: string;

  /**
   * 文件类型名称
   */
  linkFileTypeName?: string;

  /**
   * 文件内容ID
   */
  linkFileContentId?: string | number;

  /**
   * 文件占用空间(B)
   */
  fileSpace?: number;

}

export interface FolderFileQuery extends PageQuery {

  /**
   * 文件名称
   */
  fileName?: string;

  /**
   * 是否文件夹
   */
  isFolder?: number;

  /**
   * 用户ID
   */
  linkUserId?: string | number;

  /**
   * 编辑状态
   */
  editing?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



