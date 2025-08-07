export interface OrderVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 商品标题
   */
  subject: string;

  /**
   * 金额
   */
  amount: number;

  /**
   * 支付渠道编码
   */
  payWayCode: string;

  /**
   * 支付状态 1：支付成功 0:待支付 2: 支付关闭
   */
  status: number;

  createTime: string;

  orderNo: string;
}

export interface OrderForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 商品标题
   */
  subject?: string;

  /**
   * 金额
   */
  amount?: number;

  /**
   * 支付渠道编码
   */
  payWayCode?: string;

  /**
   * 支付状态 1：支付成功 0:待支付 2: 支付关闭
   */
  status?: number;

}

export interface OrderQuery extends PageQuery {

  /**
   * 商品标题
   */
  subject?: string;

  /**
   * 支付渠道编码
   */
  payWayCode?: string;

  /**
   * 支付状态 1：支付成功 0:待支付 2: 支付关闭
   */
  status?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



