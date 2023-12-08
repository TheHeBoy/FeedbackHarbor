import { store } from '@/store';
import { defineStore } from 'pinia';
import { getAccessToken, removeToken } from '@/utils/auth';
import { CACHE_KEY, useCache } from '@/hooks/web/useCache';
import * as LoginApi from '@harbor/apis/src/login';
import { usePermissionStore } from '@/store/modules/permission';
import { logout } from '@harbor/apis/src/login';

const { wsCache } = useCache();

interface UserVO {
  id: number;
  avatar: string;
  nickname: string;
  userType: number;
}

interface UserInfoVO {
  user: UserVO;
  isSetUser: boolean;
}

export const useUserStore = defineStore('admin-user', {
  state: (): UserInfoVO => ({
    user: {
      id: 0,
      avatar: '',
      nickname: '',
      userType: 0,
    },
    isSetUser: false,
  }),
  getters: {
    getIsSetUser(): boolean {
      return this.isSetUser;
    },
    getUser(): UserVO {
      return this.user;
    },
  },
  actions: {
    async setUserInfoAction() {
      if (!getAccessToken()) {
        this.resetState();
        return null;
      }
      let userInfo = wsCache.get(CACHE_KEY.USER);
      if (!userInfo) {
        userInfo = await LoginApi.getUserInfo();
      }

      this.user = userInfo;
      this.isSetUser = true;
      wsCache.set(CACHE_KEY.USER, userInfo);
    },
    async loginOut() {
      await LoginApi.logout();
      // 删除用户信息
      wsCache.delete(CACHE_KEY.USER);
      // 删除权限信息
      usePermissionStore().removePermission();
      // 删除租户信息
      wsCache.delete(CACHE_KEY.TENANT);
      // 删除token
      removeToken();
      this.resetState();
    },
    resetState() {
      this.isSetUser = false;
      this.user = {
        id: 0,
        avatar: '',
        nickname: '',
        userType: 0,
      };
    },
  },
});

export const useUserStoreWithOut = () => {
  return useUserStore(store);
};
