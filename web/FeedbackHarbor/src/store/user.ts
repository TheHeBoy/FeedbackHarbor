import store from './index';
import { getAccessToken, removeToken } from '@/utils/auth';
import { CACHE_KEY, useCache } from '@/hooks/useCache';
import { getUserInfo, logout } from '@/api/login';
import { useloginStoreWithOut } from './login';

const { wsCache } = useCache();

export const useUserStore = defineStore('admin-user', {
  state: () => ({
    user: { id: -1, avatar: '', nickname: '', userType: -1 },
  }),
  getters: {
  },
  actions: {
    async setUserInfoAction() {
      if (!getAccessToken()) {
        return null;
      }
      let userInfo = wsCache.get(CACHE_KEY.USER);
      if (!userInfo) {
        userInfo = await getUserInfo();
      }

      wsCache.set(CACHE_KEY.USER, userInfo);
      this.user = userInfo;
    },
    async loginOut() {
      await logout()
      removeToken()
      wsCache.delete(CACHE_KEY.USER)
      this.resetState()
    },
    resetState() {
      this.user = {
        id: -1,
        avatar: '',
        nickname: '',
        userType: -1,
      }
    },
    isLogin() {
      return this.user.id != -1;
    },
    isLoginAndShwolog() {
      const flag = this.isLogin()
      if (!flag) {
        useloginStoreWithOut().open();
      }
      return flag;
    }
  },
});

export const useUserStoreWithOut = () => {
  return useUserStore(store);
};
