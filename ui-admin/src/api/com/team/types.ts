export interface TeamVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 团队名称
   */
  teamName: string;

  /**
   * 团队描述
   */
  teamDesc: string;

}

export interface TeamForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 团队名称
   */
  teamName?: string;

  /**
   * 团队描述
   */
  teamDesc?: string;

}

export interface TeamQuery extends PageQuery {

  /**
   * 团队名称
   */
  teamName?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



