export interface AnalysisRecordVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 设备ID
   */
  deviceId: string | number;

  /**
   * 分析时间
   */
  analysisTime: string;

  /**
   * 违规敏感词
   */
  sensitiveWords: string;

  /**
   * 拨号开始时间
   */
  callTime: string;

  /**
   * 通话时长(s)
   */
  callTimeLength: number;

  /**
   * 使用人姓名
   */
  userName: string;

  /**
   * 本机号码
   */
  localPhoneNumber: string;

  /**
   * 电话状态
   */
  phoneStatus: string;

  /**
   * 电话号码
   */
  phoneNumber: string;

  /**
   * 人员姓名
   */
  phoneName: string;

  /**
   * 图片地址
   */
  picUrl: string;

}

export interface AnalysisRecordForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 设备ID
   */
  deviceId?: string | number;

  /**
   * 分析时间
   */
  analysisTime?: string;

  /**
   * 违规敏感词
   */
  sensitiveWords?: string;

  /**
   * 拨号开始时间
   */
  callTime?: string;

  /**
   * 通话时长(s)
   */
  callTimeLength?: number;

  /**
   * 使用人姓名
   */
  userName?: string;

  /**
   * 本机号码
   */
  localPhoneNumber?: string;

  /**
   * 电话状态
   */
  phoneStatus?: string;

  /**
   * 电话号码
   */
  phoneNumber?: string;

  /**
   * 人员姓名
   */
  phoneName?: string;

  /**
   * 图片地址
   */
  picUrl?: string;

}

export interface AnalysisRecordQuery extends PageQuery {

  /**
   * 设备ID
   */
  deviceId?: string | number;

  /**
   * 违规敏感词
   */
  sensitiveWords?: string;

  /**
   * 拨号开始时间
   */
  callTime?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



