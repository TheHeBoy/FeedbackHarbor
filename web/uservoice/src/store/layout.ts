import store from './index';

const useLayoutStore = defineStore({
  id: 'layout',
  state: () => {
    return {
      activeIndex: '/',
    };
  },
  persist: true,
  getters: {
    getIsShow: (state) => state.activeIndex,
  },
  actions: {
    setActiveIndex(activeIndex: string) {
      this.activeIndex = activeIndex;
    },
  },
});

export const useLayoutStoreWithOut = () => {
  return useLayoutStore(store);
};
