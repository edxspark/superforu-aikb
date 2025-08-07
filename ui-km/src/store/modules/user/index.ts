import { defineStore } from 'pinia';
import {
  login as userLogin,
  logout as userLogout,
  getUserInfo,
  LoginData,
} from '@/api/user';
import { setToken, clearToken } from '@/utils/auth';
import { removeRouteListener } from '@/utils/route-listener';
import { UserState } from './types';
import useAppStore from '../app';

const useUserStore = defineStore('user', {
  state: (): UserState => ({
    id: '',
    name: undefined,
    avatar: undefined,
    job: undefined,
    organization: undefined,
    location: undefined,
    email: undefined,
    introduction: undefined,
    personalWebsite: undefined,
    jobName: undefined,
    organizationName: undefined,
    locationName: undefined,
    phone: undefined,
    registrationDate: undefined,
    accountId: undefined,
    certification: undefined,
    role: '',
  }),

  getters: {
    userInfo(state: UserState): UserState {
      return { ...state };
    },
  },

  actions: {
    switchRoles() {
      return new Promise((resolve) => {
        this.role = this.role === 'user' ? 'admin' : 'user';
        resolve(this.role);
      });
    },
    // Set user's information
    setInfo(partial: Partial<UserState>) {
      this.$patch(partial);
    },

    // Reset user's information
    resetInfo() {
      this.$reset();
    },

    // Get user's information
    async info() {
      const res = await getUserInfo();
      const spfUserInfo: any = { ...res.data, userId: res.data.id };

      // 存入数据
      localStorage.setItem('spf_user_info', JSON.stringify(spfUserInfo));
      this.setInfo(res.data);
    },

    // Login
    async login(loginForm: LoginData) {
      try {
        const res = await userLogin(loginForm);
        // 存入数据
        localStorage.setItem('spf_app_server_host', import.meta.env.VITE_API_BASE_URL);
        localStorage.setItem('spf_app_upload_host', import.meta.env.VITE_APP_UPLOAD_HOST);
        localStorage.setItem('spf_client_id', import.meta.env.VITE_APP_CLIENT_ID);
        localStorage.setItem('spf_language', localStorage.getItem('arco-locale') || 'zh');
        localStorage.setItem('spf_file_editors_host', '/editors.html');
        localStorage.setItem("spf_office_template_url", import.meta.env.VITE_OFFICE_TEMPLATE_URL);

        const token = `Bearer ${res.data.access_token}`;
        localStorage.setItem('spf_token', token);
        setToken(token);
      } catch (err) {
        clearToken();
        throw err;
      }
    },
    logoutCallBack() {
      const appStore = useAppStore();
      this.resetInfo();
      clearToken();
      removeRouteListener();
      appStore.clearServerMenu();
    },
    // Logout
    async logout() {
      try {
        await userLogout();
      } finally {
        this.logoutCallBack();
      }
    },
  },
});

export default useUserStore;
