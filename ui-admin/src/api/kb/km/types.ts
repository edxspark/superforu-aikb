export interface KmVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 名称
   */
  name: string;

  /**
   * 介绍
   */
  mark: string;

  /**
   * 最大磁盘空间(B)
   */
  maxSpace: number;

  /**
   * 已使用磁盘空间(B)
   */
  usedSpace: number;

  /**
   * 创建时间
   */
  createTime: string;

  /**
   * 创建人
   */
  createBy: number;

  /**
   * 更新时间
   */
  updateTime: string;

}

export interface KmForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 名称
   */
  name?: string;

  /**
   * 介绍
   */
  mark?: string;

}

export interface KmQuery extends PageQuery {

  /**
   * 名称
   */
  name?: string;

  /**
   * 介绍
   */
  mark?: string;

  /**
   * 创建人
   */
  createBy?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



