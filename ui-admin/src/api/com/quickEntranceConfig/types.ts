export interface QuickEntranceConfigVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 排序
   */
  sort: number;

  /**
   * 文件类型id关联
   */
  linkFileTypeId: string | number;

  /**
   * 是否初始化（0：初始化 1:未初始化）
   */
  status: number;

  /**
   * 配置内容
   */
  value: string;

}

export interface QuickEntranceConfigForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 排序
   */
  sort?: number;

  /**
   * 文件类型id关联
   */
  linkFileTypeId?: string | number;

  /**
   * 是否初始化（0：初始化 1:未初始化）
   */
  status?: number;

  /**
   * 配置内容
   */
  value?: string;

}

export interface QuickEntranceConfigQuery extends PageQuery {

  /**
   * 排序
   */
  sort?: number;

  /**
   * 文件类型id关联
   */
  linkFileTypeId?: string | number;

  /**
   * 是否初始化（0：初始化 1:未初始化）
   */
  status?: number;

  /**
   * 配置内容
   */
  value?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



