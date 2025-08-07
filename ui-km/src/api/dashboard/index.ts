import axios from 'axios';

export interface KmVO {
  rows: any;
  id: string | number;
  name: string;
  linkOssId: string;
  mark: string;
  maxSpace: number;
  usedSpace: number;
  type: string;
  linkUserAccount: string;
  isAiOpen: string;
  picOSSUrl: string;
}

export interface addRepository {
  linkKmId: string;
  linkTeamId: string;
}
export interface deleteRepository {
  ids: string;
}
export interface teamList {
  id: string;
  teamName: string;
  teamDesc: string;
  roleType: string;
  picUrl: string;
}
export interface teamLists {
  rows: any;
  createBy: number;
  createByName: string;
  createTime: string;
  noticeContent: string;
  noticeId: number;
  noticeTitle: string;
  noticeType: string;
  remark: string;
  status: string;
}
export interface searchSynergyList {
  id: string;
  linkKmId: string;
  linkTeamId: string;
  createTime: string;
  createBy: string;
}
export interface addkmShare {
  linkKmId: string;
  accessPermission: string;
  accessPassword: string;
}
export interface getOneByLinkKmId {
  id: string;
  linkKmId: string;
  shareId: string;
  shareURL: string;
  accessPermission: string;
  accessPassword: string;
  editorURL: string;
}

export interface deleteShare {
  id: string;
}
export interface fileRecentlyList {
  total: number;
  rows: any[];
}

export interface fileContent {
  data: any[];
}
export interface searchHistoryList {
  total: number;
  rows: any[];
}


export interface folderFileList {
  id: string;
  fileName: string;
  parentId: string;
  catalogIds: string;
  isFolder: string;
  linkKmId: any;
  editing: string;
  fileKmTreeData: object;
}

export interface addFolderFile {
  fileName: string;
  parentId: string | number;
  linkKmId: string;
  linkFileTypeCode: string;
  linkFileTypeName: string;
  fileKmTreeData: object;
  fileExtension: string;
}
export interface fileESSearch {
  key: string;
}

export interface editFolderFile {
  id: string;
  fileName: string;
}
export interface deleteFolderFile {
  ids: string;
}
export interface recoverRecycle {
  id: string;
}

export interface packageItem{
  rows: any,
  packageCode: string,
  packageName: string,
  unit: string,
  price: number,
  discount: string,
  min: number,
  desc: string
}

export interface confirmPayParams{
  payWay: string,
  packageCode: string,
  number: number,
  promotion: string
}

export interface confirmPayData{
  msg: string, // QRCode
  data: string // OrderNo
}
export interface queryOrderStatusData{
  status: string
}

export interface getPromotionItem{
  promotionValue: string
}
export interface getPromotionData{
  data: getPromotionItem
}

export interface dictData{
  data: any[]
}

// 获取数字字典数据
export function getDictList(dictLabel: string){
  return axios.get<dictData>(`/api/system/dict/data/type/${dictLabel}`);
}

// 查询支付订单状态
export  function getPromotion(promotionCode: string){
  return axios.get<getPromotionData>(`/api/com/userPackagePurchase/getPromotion/${promotionCode}`);
}

// 查询支付订单状态
export  function queryOrderStatus(orderNo: string){
  return axios.get<queryOrderStatusData>(`/api/com/userPackagePurchase/queryOrderStatus/${orderNo}`);
}

// 获取支付二维码
export function confirmPayFunction(params:confirmPayParams) {
  return axios.post<confirmPayData>('/api/com/userPackagePurchase/purchaseItem',params);
}

// 获取套餐列表
export function getPackageItems() {
  return axios.get<{rows:packageItem[]}>('/api/com/userPackagePurchase/listItems');
}

// 获取知识库列表
export function dashboardListAll() {
  return axios.get<KmVO>('/api/kb/km/list');
}

// 新增知识库
export function addkmRepository(data: addRepository) {
  return axios.post<addRepository>('/api/kb/km', data);
}

// 更新知识库
export function updateKmRepository(data: addRepository) {
  return axios.put<addRepository>('/api/kb/km', data);
}

// 删除知识库
export const deletekmRepository = (ids: string) => {
  return axios({
    url: `/api/kb/km/${ids}`,
    method: 'delete',
  });
};

// 获取知识库详细信息

export const dashboardListDetail = (id: string) => {
  return axios({
    url: `/api/kb/km/${id}`,
    method: 'get',
  });
};

// 查询团队管理列表

export function teamList() {
  return axios.get<teamList[]>('/api/com/team/list');
}

// 查询协同管理信息

export function searchSynergyList(params: { linkKmId: string }) {
  return axios.get<searchSynergyList[]>(
    '/api/kb/kmCollaboration/listByLinkKmId',
    { params }
  );
}

// 新增协同管理

export function addSynergyList(data: addRepository) {
  return axios.post<addRepository[]>('/api/kb/kmCollaboration', data);
}

// 新增分享预览

export function addkmShare(data: addkmShare) {
  return axios.post<addkmShare[]>('/api/kb/kmShare', data);
}

// 根据知识库ID获取分享信息

export function getOneByLinkKmId(params: { linkKmId: string }) {
  return axios.get<getOneByLinkKmId[]>('/api/kb/kmShare/getOneByLinkKmId', {
    params,
  });
}

export function getKmShareInfo(shareId:string) {
  return axios.get<getOneByLinkKmId>(`/api/kb/kmShare/getKmShareInfo/${shareId}`);
}

// 删除分享预览

export function deleteShare(id: deleteShare) {
  return axios.delete<deleteShare>(`/api/kb/kmShare/${id}`);
}

// 查询团队管理列表

export function fileRecentlyList(params: { pageSize: number, pageNum: number, orderByColumn: string, isAsc: string }) {
  return axios.get<fileRecentlyList>('/api/kb/fileRecently/list', { params });
}


// 获取文档内容
export function getFileContent(id: string) {
  return axios.get<fileContent>(`/api/kb/fileContent/${id}`);
}

// 文档库-查询文件夹-文件列表

export function folderFileList(params: {
  linkKmId: string;
  parentId: string;
  pageSize: number;
  pageNum: number;
  orderByColumn: string;
  isAsc: string;
}) {
  return axios.get<folderFileList[]>('/api/kb/folderFile/list', { params });
}



// AI 向量华
export function aiFolderFileEmbedding(fileId: string) {
  return axios.post<addFolderFile>(`/api/kb/folderFile/embedding/${fileId}`);
}

// 新增文件夹&文件
export function addFolderFile(data: addFolderFile) {
  return axios.post<addFolderFile>('/api/kb/folderFile', data);
}

// 修改文件夹&文件
export function editFolderFile(data: editFolderFile) {
  return axios.put<editFolderFile>('/api/kb/folderFile', data);
}

// 删除文件夹&文件
export function deleteFolderFile(ids: deleteFolderFile) {
  return axios.delete<deleteFolderFile>(`/api/kb/folderFile/${ids}`);
}

// 查询知识库文档目录树
export function listTreeData(params: { id: string }) {
  return axios.get<getOneByLinkKmId[]>('/api/kb/km/listTreeData', { params });
}

// 更新知识库文档目录树
export function updateTreeData(params: { id: string; fileKmTreeData: string }) {
  return axios.post<addFolderFile>('/api/kb/km/updateTreeData', params, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
}

// 文档库-查询文件夹-文件列表

export function recycleList(params: {
  pageSize: number;
  pageNum: number;
  orderByColumn: string;
  isAsc: string;
}) {
  return axios.get<folderFileList[]>('/api/kb/recycle/list', { params });
}

// 删除文件夹&文件

export function deleteRecycle(ids: deleteFolderFile) {
  return axios.delete<deleteFolderFile>(`/api/kb/recycle/${ids}`);
}

// 恢复文件夹&文件

export function recoverRecycle(id: recoverRecycle) {
  return axios.post<recoverRecycle>(`/api//kb/recycle/recover?id=${id}`);
}

// 公告通知列表

export function noticeList(params: {
  pageSize: number;
  pageNum: number;
  orderByColumn: string;
  isAsc: string;
}) {
  return axios.get<{ rows: teamLists[] }>('/api/system/notice/list', {
    params,
  });
}

// 公告通知详情

export function noticeListDetial(id: deleteFolderFile) {
  return axios.get<teamList[]>(`/api/system/notice/${id}`);
}

// ES搜索

export function fileESSearch(key: string) {
  return axios.post<fileESSearch>(`/api/kb/folderFile/fileESSearch?key=${key}`);
}

// 查询团队管理列表

export function searchHistoryList(params: { pageSize: number, pageNum: number, orderByColumn: string, isAsc: string }) {
  return axios.get<searchHistoryList>('/api/kb/searchHistory/list', { params });
}