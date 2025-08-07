export interface DevicesVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 设备ID
   */
  deviceId: string | number;

  /**
   * 设备名称
   */
  deviceName: string;

  /**
   * 设备类型
   */
  deviceType: string;

  /**
   * 人脸识别功能
   */
  faceSwitch: string;

  /**
   * 语音识别功能
   */
  voiceSwitch: string;

  /**
   * 图片地址
   */
  picUrl: string;

}

export interface DevicesForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 设备ID
   */
  deviceId?: string | number;

  /**
   * 设备名称
   */
  deviceName?: string;

  /**
   * 设备类型
   */
  deviceType?: string;

  /**
   * 人脸识别功能
   */
  faceSwitch?: string;

  /**
   * 语音识别功能
   */
  voiceSwitch?: string;

  /**
   * 图片地址
   */
  picUrl?: string;

}

export interface DevicesQuery extends PageQuery {

  /**
   * 设备ID
   */
  deviceId?: string | number;

  /**
   * 设备名称
   */
  deviceName?: string;

  /**
   * 设备类型
   */
  deviceType?: string;

  /**
   * 人脸识别功能
   */
  faceSwitch?: string;

  /**
   * 语音识别功能
   */
  voiceSwitch?: string;

  /**
   * 图片地址
   */
  picUrl?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



