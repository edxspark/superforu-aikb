export interface UserPackagePromotionVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 优惠码
   */
  promotionCode: string;

  /**
   * 优惠金额
   */
  promotionValue: number;

  /**
   * 最大使用次数
   */
  maxUseCount: number;

  /**
   * 已经使用次数
   */
  usedCount: number;

}

export interface UserPackagePromotionForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 优惠码
   */
  promotionCode?: string;

  /**
   * 优惠金额
   */
  promotionValue?: number;

  /**
   * 最大使用次数
   */
  maxUseCount?: number;

  /**
   * 已经使用次数
   */
  usedCount?: number;

}

export interface UserPackagePromotionQuery extends PageQuery {

  /**
   * 优惠码
   */
  promotionCode?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



