export interface UserPackageUseDetailVO {
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
   * 本次消费数量
   */
  number: number;

  /**
   * 消费前结余
   */
  balanceBefore: number;

  /**
   * 消费后结余
   */
  balanceAfter: number;

}

export interface UserPackageUseDetailForm extends BaseEntity {
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
   * 本次消费数量
   */
  number?: number;

  /**
   * 消费前结余
   */
  balanceBefore?: number;

  /**
   * 消费后结余
   */
  balanceAfter?: number;

}

export interface UserPackageUseDetailQuery extends PageQuery {

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



