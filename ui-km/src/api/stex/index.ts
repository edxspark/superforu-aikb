import axios from 'axios';

export interface teamList {
  id: string;
  subjectName: number;
  subjectUid: string;
  pageSize: number;
  pageNum: string;
  orderByColumn: string;
  isAsc: string;
  rows: any;
}

export interface userConfigList {
  comUserSuperModuleConfigVo: any;
  comUserQuickEntranceConfigVo: any;
}
export interface ListByUser {
  id: string;
  sort: number;
  name: string;
  code: string;
  icon: string;
  color: string;
  attrType: string;
  existStatus: number;
}
export interface userConfigAddAndDelete {
  subjectName: string;
  subjectUid: string;
  ossId: string;
}
export interface getUserInfo {
  id: string;
  userName: string;
  signature: string;
  picUrl: string;
  theme: string;
  language: string;
  userPackageDetail: any
}
export interface templateList {
  id: string;
}
export interface faceSubjectUpdate {
  id: string;
  subjectName: string;
  subjectUid: string;
}

export interface addFolderFileByTemplate {
  templateId: number,
  parentId: string | number;
  linkKmId: string;
}
export interface deleteFolderFile {
  ids: string;
}
export interface faceSubjectOssId {
  ossId: string;
}
// 查询题库列表

export function examBankList
  (params: { pageSize: number, pageNum: number, orderByColumn: string, isAsc: string }) {
  return axios.get<teamList>('/api/exam/examBank/list', { params });
}

// 新增答题记录

export function examScoreAdd(data: userConfigAddAndDelete) {
  return axios.post<userConfigAddAndDelete[]>('/api/exam/examScore', data);
}