import { store } from '@/store';
import { defineStore } from 'pinia';
import { getAccessToken, removeToken } from '@/utils/auth';
import { CACHE_KEY, useCache } from '@/hooks/web/useCache';
import { loginOut, getUserLoginInfo } from '@/api/login';
import { usePermissionStore } from '@/store/modules/permission';

const { wsCache } = useCache();

interface UserVO {
  id: number;
  avatar: string;
  nickname: string;
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
        userInfo = await getUserLoginInfo();
      }

      this.user = userInfo;
      this.isSetUser = true;
      wsCache.set(CACHE_KEY.USER, userInfo);
    },
    async loginOut() {
      await loginOut();
      // 删除用户信息
      wsCache.delete(CACHE_KEY.USER);
      // 删除权限信息
      usePermissionStore().removePermission();
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
      };
    },
  },
});

export const useUserStoreWithOut = () => {
  return useUserStore(store);
};
