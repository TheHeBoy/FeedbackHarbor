import store from './index';
import { getAccessToken, removeToken } from '@/utils/auth';
import * as LoginApi from '@harbor/apis/src/login';
import { CACHE_KEY, useCache } from '@/hooks/useCache';

const { wsCache } = useCache();

export const useUserStore = defineStore('app-user', {
  state: () => ({
    user: { id: 0, avatar: '', nickname: '', userType: 0 },
    isSetUser: false,
  }),
  getters: {},
  actions: {
    async setUserInfoAction() {
      let token = getAccessToken();
      if (!token) {
        this.resetState();
        return null;
      }
      // 设置用户信息
      let userInfo = wsCache.get(CACHE_KEY.USER);
      if (!userInfo) {
        userInfo = await LoginApi.getUserInfo();
      }
      this.user = userInfo;
      this.isSetUser = true;
    },
    async loginOut() {
      await LoginApi.logout();
      removeToken();
      wsCache.delete(CACHE_KEY.USER);
      this.resetState();
    },
    resetState() {
      this.user = {
        id: 0,
        avatar: '',
        nickname: '',
        userType: 0,
      };
      this.isSetUser = false;
    },
    isLogin() {
      return !!getAccessToken() && this.isSetUser;
    },
  },
});

export const useUserStoreWithOut = () => {
  return useUserStore(store);
};
