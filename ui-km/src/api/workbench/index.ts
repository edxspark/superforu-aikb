import axios from 'axios';

export interface teamList {
  id: string;
  sort: number;
  linkFileTypeId: string;
  status: number;
  name: string;
  code: string;
  icon: string;
  color: string;
  attrType: string;
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
  configValueId: string;
  id: string;
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

export interface addFolderFileByTemplate {
  templateId:number,
  parentId: string | number;
  linkKmId: string;
}

// 查询团队管理列表

export function quickEntranceConfigList() {
  return axios.get<teamList[]>('/api/com/quickEntranceConfig/list');
}

// 查询用户配置列表

export function userConfigList() {
  return axios.get<userConfigList>('/api/com/userConfig/list');
}

// 查询超级模块列表是否已添加

export function SuperListByUser() {
  return axios.post<ListByUser[]>(
    '/api/com/userConfig/querySuperModuleConfigListByUser'
  );
}
// 查询快捷入口列表是否已添加

export function ListByUser() {
  return axios.post<ListByUser[]>(
    '/api/com/userConfig/queryQuickEntranceConfigListByUser'
  );
}

// 查询文件类型

export function fileTypeList() {
  return axios.get<ListByUser>('/api/kb/fileType/list');
}

// 使用模版创建文件
export function createByTemplate(data: addFolderFileByTemplate) {
  console.log(data)
  return axios.post<addFolderFileByTemplate>('/api/kb/folderFile/createByTemplate', data);
}


// 新增用户配置

export function addUserConfig(data: userConfigAddAndDelete) {
  return axios.post<userConfigAddAndDelete[]>('/api/com/userConfig/add', data);
}

// 移除用户配置

export function removeUserConfig(data: userConfigAddAndDelete) {
  return axios.post<userConfigAddAndDelete[]>(
    '/api/com/userConfig/remove',
    data
  );
}

// 获取用户信息详细信息

export function getUserInfo() {
  return axios.get<getUserInfo[]>('/api/com/user/getUserInfo');
}

// 修改用户信息

export function editUserInfo(data: getUserInfo) {
  return axios.post<getUserInfo[]>('/api/com/user/edit', data);
}
