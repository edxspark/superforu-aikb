export interface SensitiveWordsSettingVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 敏感词
   */
  sensitiveWords: string;

}

export interface SensitiveWordsSettingForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 敏感词
   */
  sensitiveWords?: string;

}

export interface SensitiveWordsSettingQuery extends PageQuery {

  /**
   * 敏感词
   */
  sensitiveWords?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



