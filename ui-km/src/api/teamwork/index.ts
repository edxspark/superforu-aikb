import axios from 'axios';

export interface teamList {
  rows: object;
  id: string;
  teamName: string;
  teamDesc: string;
  roleType: string;
  picUrl: string;
}

export interface addTeam {
  picUrl: string;
  teamName: string;
  teamDesc: string;
}
export interface deleteTeam {
  id: string;
}
export interface editTeam {
  picUrl: string;
  teamName: string;
  teamDesc: string;
}
export interface quitTeam {
  id: string;
}
export interface teamMatePageList {
  teamId: string;
  pageSize: string;
  pageNum: string;
  orderByColumn: string;
  isAsc: string;
}
export interface addTeamMate {
  linkTeamId: string;
  linkUserId: string;
  roleType: number;
}
export interface deleteTeamMate {
  teamId: string;
  teamMateId: string;
}

export interface templateList {
  id: string;
  name: string;
  fileTypeCode: string;
  fileTypeName: string;
  useCount: string;
  status: string;
  linkFileTemplateTypeId: string;
  attrContent: string;
}

// 查询团队管理列表

export function teamList() {
  return axios.get<teamList[]>('/api/com/team/list');
}

// 新增团队管理

export function addSynergyList(data: addTeam) {
  return axios.post<addTeam[]>('/api/com/team/add', data);
}

// 删除团队管理(解散团队)

export function deleteTeamItem(data: deleteTeam) {
  return axios.post<deleteTeam[]>('/api/com/team/delete', data);
}

// 修改团队管理

export function editTeam(data: editTeam) {
  return axios.post<editTeam[]>('/api/com/team/edit', data);
}

// 退出团队管理

export function quitTeam(data: quitTeam) {
  return axios.post<quitTeam[]>('/api/com/team/quit', data);
}

// 查询团队管理列表
export function teamMatePageList(params: {
  teamId: string;
  pageSize: number;
  pageNum: number;
  orderByColumn: string;
  isAsc: string;
}) {
  return axios.get<teamMatePageList[]>('/api/com/teamMate/pageList', {
    params,
  });
}

// 成功邀请用户加入团队

export function addTeamMate(data: addTeamMate) {
  return axios.post<addTeamMate[]>('/api/com/teamMate/add', data);
}

// 删除团队成员管理

export function deleteTeamMate(data: deleteTeamMate) {
  return axios.post<deleteTeamMate[]>('/api/com/teamMate/delete', data);
}

// 查询文档模板列表

export function fileTemplateTypeList() {
  return axios.get<teamList[]>('/api/kb/fileTemplateType/list');
}

// 查询文档模板类型列表

export function templateList(params: {
  pageSize: number;
  pageNum: number;
  orderByColumn: string;
  isAsc: string;
  linkFileTemplateTypeId: string;
}) {
  return axios.get<teamList[]>('/api/kb/fileTemplate/list', { params });
}

// 获取文档模板详细信息

export function templateListDetail(id: templateList) {
  return axios.get<teamList[]>(`/api/kb/fileTemplate/${id}`);
}
