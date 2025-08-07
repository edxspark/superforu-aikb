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
// 查询人脸识别库列表

export function faceSubjectList
  (params: { pageSize: number, pageNum: number, orderByColumn: string, isAsc: string }) {
  return axios.get<teamList>('/api/face/faceSubject/list', { params });
}

// 新增人脸识别库

export function faceSubjectAdd(data: userConfigAddAndDelete) {
  return axios.post<userConfigAddAndDelete[]>('/api/face/faceSubject', data);
}

// 修改人脸识别库

export function faceSubjectUpdate(data: faceSubjectUpdate) {
  return axios.put<faceSubjectUpdate[]>('/api/face/faceSubject', data);
}

// 获取单条人脸识别库信息

export function templateListDetail(id: templateList) {
  return axios.get<teamList[]>(`/api/face/faceSubject/list/${id}`);
}

// 删除单个人脸图片

export function faceSubjectOssId(id: deleteFolderFile, ossId: faceSubjectOssId) {
  return axios.delete<deleteFolderFile>(`/api/face/faceSubject/${id.ids}/${ossId}`);
}
// 删除人脸识别库

export function deleteFaceId(ids: deleteFolderFile) {
  return axios.delete<deleteFolderFile>(`/api/face/faceSubject/${ids}`);
}
// 新增单个人脸图片

export function faceSubjectPicAdd(id: deleteFolderFile, ossId: faceSubjectOssId) {
  return axios.post<deleteFolderFile>(`/api/face/faceSubject/add/${id.ids}/${ossId}`);
}