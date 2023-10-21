import { defineStore } from 'pinia';

interface AppState {
  userInfo: string;
}

export const useAppStore = defineStore('app', {
  state: (): AppState => {
    return {
      userInfo: 'userInfo',
    };
  },
  getters: {},
});

export default useAppStore();
