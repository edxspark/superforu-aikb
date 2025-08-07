export interface UserPackagePurchaseVO {
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
   * 价格
   */
  price: number;

  /**
   * 数量
   */
  number: number;

  /**
   * 总价
   */
  total: number;

  /**
   * 支付单号
   */
  payNo: string;

  /**
   * 备注
   */
  remark: string;

}

export interface UserPackagePurchaseForm extends BaseEntity {
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
   * 价格
   */
  price?: number;

  /**
   * 数量
   */
  number?: number;

  /**
   * 总价
   */
  total?: number;

  /**
   * 支付单号
   */
  payNo?: string;

  /**
   * 备注
   */
  remark?: string;

}

export interface UserPackagePurchaseQuery extends PageQuery {

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
   * 支付单号
   */
  payNo?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



