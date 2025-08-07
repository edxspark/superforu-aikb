export interface UserVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 账号
   */
  userAccount: string;

  /**
   * 名称
   */
  userName: string;

  /**
   * 密码
   */
  userPsw: string;

  /**
   * 签名
   */
  signature: string;

  /**
   * 图片地址
   */
  picUrl: string;

  /**
   * 语言类型（0：英文，1：中文）
   */
  language: number;

  /**
   * 主题类型（0：黑夜主题、1：白天主题:2：跟随系统）
   */
  theme: number;

}

export interface UserForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 账号
   */
  userAccount?: string;

  /**
   * 名称
   */
  userName?: string;

  /**
   * 密码
   */
  userPsw?: string;

  /**
   * 签名
   */
  signature?: string;

  /**
   * 图片地址
   */
  picUrl?: string;

  /**
   * 语言类型（0：英文，1：中文）
   */
  language?: number;

  /**
   * 主题类型（0：黑夜主题、1：白天主题:2：跟随系统）
   */
  theme?: number;

}

export interface UserQuery extends PageQuery {

  /**
   * 账号
   */
  userAccount?: string;

  /**
   * 名称
   */
  userName?: string;

  /**
   * 密码
   */
  userPsw?: string;

  /**
   * 签名
   */
  signature?: string;

  /**
   * 图片地址
   */
  picUrl?: string;

  /**
   * 语言类型（0：英文，1：中文）
   */
  language?: number;

  /**
   * 主题类型（0：黑夜主题、1：白天主题:2：跟随系统）
   */
  theme?: number;

  /**
   * 日期范围参数
   */
  params?: any;
}



