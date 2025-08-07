export interface FileTemplateTypeVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 名称
   */
  name: string;

  /**
   * 排序
   */
  sort: number;

}

export interface FileTemplateTypeForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 名称
   */
  name?: string;

  /**
   * 排序
   */
  sort?: number;

}

export interface FileTemplateTypeQuery extends PageQuery {

  /**
   * 名称
   */
  name?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



