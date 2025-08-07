import axios from 'axios';
import type { RouteRecordNormalized } from 'vue-router';
import { UserState } from '@/store/modules/user/types';

export interface LoginData {
  phoneNumber: string;
  password: string;
}
export interface RegisterData {
  phoneNumber: string;
  password: string;
  linkInviterId: string;
}
export interface CodeData {
  phoneNumber: string;
  smsType: string;
}
export interface LoginRes {
  token: string;
  access_token: string;
}
// 登录
export function login(data: LoginData) {
  return axios.post<LoginRes>('/api/com/user/auth/login', data);
}
// 退出登录
export function logout() {
  return axios.post<LoginRes>('/api/auth/logout');
}
// 注册
export function register(data: RegisterData) {
  return axios.post<LoginRes>('/api/com/user/auth/register', data);
}
// 用户发送验证码
export function sendSmsCode(data: CodeData) {
  return axios.post<LoginRes>('/api/com/user/sms/send-sms-code', data);
}
export function getUserInfo() {
  return axios.get<UserState>('/api/com/user/getUserInfo');
}

export function getMenuList() {
  return axios.post<RouteRecordNormalized[]>('/api/user/menu');
}
