import axios from 'axios';

export interface Team {
  id: string;
  deviceId: string;
  deviceName: string;
  deviceType: string;
  faceSwitch: string;
  voiceSwitch: string;
}
export type TeamList = Team[];
export interface ApiResponse {
  rows: Team[];
  total: number;
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
  id: string;
  sensitiveWords: string;
}
export interface sensitiveWordsSettingAdd {
  sensitiveWords: string;
}
export interface devicesUpdate {
  id: string;
  deviceId: string;
  deviceName: string;
  deviceType: string;
  faceSwitch: string;
  voiceSwitch: string;
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
// 查询电话机设备列表

export function devicesList
  (params: { pageSize: number, pageNum: number, orderByColumn: string, isAsc: string }) {
  return axios.get<ApiResponse>('/api/voice/devices/list', { params });
}

// 新增电话机设备

export function devicesAdd(data: devicesUpdate) {
  return axios.post<devicesUpdate[]>('/api/voice/devices', data);
}

// 修改人脸识别库

export function devicesUpdate(data: devicesUpdate) {
  return axios.put<devicesUpdate[]>('/api/voice/devices', data);
}

// 新增敏感词设置

export function sensitiveWordsSettingAdd(data: sensitiveWordsSettingAdd) {
  return axios.post<sensitiveWordsSettingAdd[]>('/api/voice/sensitiveWordsSetting', data);
}

// 查询敏感词设置列表

export function sensitiveWordsSettingList
  (params: { pageSize: number, pageNum: number, orderByColumn: string, isAsc: string }) {
  return axios.get<ApiResponse>('/api/voice/sensitiveWordsSetting/list', { params });
}

// 修改敏感词设置

export function sensitiveWordsSettingUpdate(data: userConfigAddAndDelete) {
  return axios.put<userConfigAddAndDelete[]>('/api/voice/sensitiveWordsSetting', data);
}

// 删除敏感词设置

export function deletesensitiveWordsSettingId(ids: deleteFolderFile) {
  return axios.delete<deleteFolderFile>(`/api/voice/sensitiveWordsSetting/${ids}`);
}

// 新增语音分析记录

export function analysisRecordAdd(data: userConfigAddAndDelete) {
  return axios.post<userConfigAddAndDelete[]>('/api/voice/analysisRecord', data);
}

// 查询语音分析记录列表

export function analysisRecordList
  (params: { pageSize: number, pageNum: number, orderByColumn: string, isAsc: string }) {
  return axios.get<ApiResponse>('/api/voice/analysisRecord/list', { params });
}

