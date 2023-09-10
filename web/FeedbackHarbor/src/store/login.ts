import store from './index';

const useloginStore = defineStore({
  id: 'login',
  state: () => {
    return {
      isShow: false,
    };
  },
  getters: {
    getIsShow: (state) => state.isShow,
  },
  actions: {
    open() {
      this.isShow = true;
    },
    close() {
      this.isShow = false;
    },
  },
});

export const useloginStoreWithOut = () => {
  return useloginStore(store);
};
