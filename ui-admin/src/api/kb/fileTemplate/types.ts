export interface FileTemplateVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 模板名称
   */
  name: string;

  /**
   * 文件类型
   */
  fileTypeCode: string;

  /**
   * 文件类型名称
   */
  fileTypeName: string;

  /**
   * 引用次数
   */
  useCount: number;

  /**
   * 状态
   */
  status: number;

  /**
   * 编辑器URL
   */
  editorURL: string;

  /**
   * 内容
   */
  attrContent?: string;

  /**
   * 模板类型ID
   */
  linkFileTemplateTypeId: string | number;

}

export interface FileTemplateForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 模板名称
   */
  name?: string;

  /**
   * 文件类型
   */
  fileTypeCode?: string;

  /**
   * 文件类型名称
   */
  fileTypeName?: string;

  /**
   * 引用次数
   */
  useCount?: number;

  /**
   * 状态
   */
  status?: number;

  /**
   * 模板类型ID
   */
  linkFileTemplateTypeId?: string | number;

  /**
   * 后缀名
   */
  attrType?: string;

  /**
   * 内容
   */
  attrContent?: string;

  /**
   * 图片地址
   */
  picUrl?: string;

}

export interface FileTemplateQuery extends PageQuery {

  /**
   * 文件类型
   */
  fileTypeCode?: string;

  /**
   * 文件类型名称
   */
  fileTypeName?: string;

  /**
   * 模版名称
   */
  name?: string;

  /**
   * 模版类型
   */
  linkFileTemplateTypeId?: string;

  /**
   * 日期范围参数
   */
  params?: any;
}
