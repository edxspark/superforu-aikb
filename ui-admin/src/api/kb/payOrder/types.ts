export interface PayOrderVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 订单ID
   */
  orderNo: string;

  /**
   * 支付项目
   */
  goodName: string;

  /**
   * 金额
   */
  amount: number;

  /**
   * 支付渠道ID
   */
  linkPayWayId: string | number;

  /**
   * 支付渠道名称
   */
  linkPayWayName: string;

  /**
   * 支付状态
   */
  status: number;

  /**
   * 用户ID
   */
  linkUserId: string | number;

  /**
   * 用户名称
   */
  linkUserName: string;

}

export interface PayOrderForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 订单ID
   */
  orderNo?: string;

  /**
   * 支付项目
   */
  goodName?: string;

  /**
   * 金额
   */
  amount?: number;

  /**
   * 支付渠道ID
   */
  linkPayWayId?: string | number;

  /**
   * 支付渠道名称
   */
  linkPayWayName?: string;

  /**
   * 支付状态
   */
  status?: number;

  /**
   * 用户ID
   */
  linkUserId?: string | number;

  /**
   * 用户名称
   */
  linkUserName?: string;

}

export interface PayOrderQuery extends PageQuery {

  /**
   * 订单ID
   */
  orderNo?: string;

  /**
   * 支付状态
   */
  status?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



