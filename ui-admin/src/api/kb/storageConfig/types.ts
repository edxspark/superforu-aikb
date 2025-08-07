export interface StorageConfigVO {
  /**
   * 主键
   */
  id: string | number;

  /**
   * 名称
   */
  name: string;

  /**
   * 团队成员ID
   */
  linkTeamId: string | number;

  /**
   * 配置信息JSON
   */
  configJson: string;

}

export interface StorageConfigForm extends BaseEntity {
  /**
   * 主键
   */
  id?: string | number;

  /**
   * 名称
   */
  name?: string;

  /**
   * 团队成员ID
   */
  linkTeamId?: string | number;

  /**
   * 配置信息JSON
   */
  configJson?: string;

  /**
   * 图片地址
   */
  picUrl?: string;

}

export interface StorageConfigQuery extends PageQuery {

  /**
   * 名称
   */
  name?: string;

  /**
   * 团队成员ID
   */
  linkTeamId?: string | number;

    /**
     * 日期范围参数
     */
    params?: any;
}



