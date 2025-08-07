export interface UserPackageDetailVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 用户ID
   */
  linkUserId: string | number;

  /**
   * 编码
   */
  packageCode: string;

  /**
   * 名称
   */
  packageName: string;

  /**
   * 单位
   */
  unit: string;

  /**
   * 数量
   */
  value: number;

}

export interface UserPackageDetailForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 用户ID
   */
  linkUserId?: string | number;

  /**
   * 编码
   */
  packageCode?: string;

  /**
   * 名称
   */
  packageName?: string;

  /**
   * 单位
   */
  unit?: string;

  /**
   * 数量
   */
  value?: number;

}

export interface UserPackageDetailQuery extends PageQuery {

  /**
   * 用户ID
   */
  linkUserId?: string | number;

  /**
   * 编码
   */
  packageCode?: string;

  /**
   * 名称
   */
  packageName?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



