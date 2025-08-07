export interface FaceSubjectVO {
  /**
   * ID
   */
  id: string | number;

  /**
   * 名称
   */
  subjectName: string;

  /**
   * UID
   */
  subjectUid: string | number;

}

export interface FaceSubjectForm extends BaseEntity {
  /**
   * ID
   */
  id?: string | number;

  /**
   * 名称
   */
  subjectName?: string;

  /**
   * UID
   */
  subjectUid?: string | number;

}

export interface FaceSubjectQuery extends PageQuery {

  /**
   * 名称
   */
  subjectName?: string;

  /**
   * UID
   */
  subjectUid?: string | number;

    /**
     * 日期范围参数
     */
    params?: any;
}



