export interface FileTypeVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 类型名称
   */
  name: string;

  /**
   * 类型ID
   */
  code: string;

  /**
   * 序号
   */
  sort: number;

  /**
   * 类型ICON
   */
  icon: string;

  /**
   * 类型颜色
   */
  color: string;

  /**
   * 后缀名
   */
  attrType: string;

}

export interface FileTypeForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 类型名称
   */
  name?: string;

  /**
   * 类型ID
   */
  code?: string;

  /**
   * 序号
   */
  sort?: number;

  /**
   * 类型ICON
   */
  icon?: string;

  /**
   * 类型颜色
   */
  color?: string;

  /**
   * 后缀名
   */
  attrType?: string;

}

export interface FileTypeQuery extends PageQuery {

  /**
   * 类型名称
   */
  name?: string;

  /**
   * 类型ID
   */
  code?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



