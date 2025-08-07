export interface CallbackVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 订单编号
   */
  orderNo: number;

  /**
   * 通知状态：等待通知、通知成功、通知失败、其他
   */
  status: number;

  /**
   * 通知次数
   */
  count: number;

}

export interface CallbackForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 订单编号
   */
  orderNo?: number;

  /**
   * 通知状态：等待通知、通知成功、通知失败、其他
   */
  status?: number;

  /**
   * 通知次数
   */
  count?: number;

}

export interface CallbackQuery extends PageQuery {

  /**
   * 订单编号
   */
  orderNo?: number;

  /**
   * 通知状态：等待通知、通知成功、通知失败、其他
   */
  status?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



