export interface SuperModuleConfigVO {
  /**
   * ID
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

  /**
   * 用户等级id关联
   */
  linkUserEquityId: string | number;

  /**
   * 是否初始化（0：初始化 1:未初始化）
   */
  status: number;

  /**
   * 配置内容
   */
  value: string;

  /**
   * 模块ICON
   */
  icon: string;

  /**
   * 模块颜色
   */
  color: string;

  /**
   * 打开方式
   */
  openWay: string;

}

export interface SuperModuleConfigForm extends BaseEntity {
  /**
   * ID
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

  /**
   * 用户等级id关联
   */
  linkUserEquityId?: string | number;

  /**
   * 是否初始化（0：初始化 1:未初始化）
   */
  status?: number;

  /**
   * 配置内容
   */
  value?: string;

  /**
   * 模块ICON
   */
  icon?: string;

  /**
   * 模块颜色
   */
  color?: string;

  /**
   * 打开方式
   */
  openWay?: string;

}

export interface SuperModuleConfigQuery extends PageQuery {

  /**
   * 名称
   */
  name?: string;

  /**
   * 打开方式
   */
  openWay?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



