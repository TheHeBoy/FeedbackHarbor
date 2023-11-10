import { defineStore } from 'pinia';
import { store } from '@/store';
import { cloneDeep } from 'lodash-es';
import remainingRouter from '@/router/modules/remaining';
import { generateRoute, flatMultiLevelRoutes } from '@/utils/routerHelper';
import { CACHE_KEY, useCache } from '@/hooks/web/useCache';
import { getPermissionInfo } from '@/api/login';
import router from '@/router';
import { RouteRecordRaw } from 'vue-router';

const { wsCache } = useCache();

export interface PermissionState {
  routers: AppRouteRecordRaw[];
  addRouters: AppRouteRecordRaw[];
  menuTabRouters: AppRouteRecordRaw[];
  isSetPermission: boolean;
}

export const usePermissionStore = defineStore('permission', {
  state: (): PermissionState => ({
    routers: [],
    addRouters: [],
    menuTabRouters: [],
    isSetPermission: false,
  }),
  getters: {
    getRouters(): AppRouteRecordRaw[] {
      return this.routers;
    },
    getAddRouters(): AppRouteRecordRaw[] {
      return flatMultiLevelRoutes(cloneDeep(this.addRouters));
    },
    getMenuTabRouters(): AppRouteRecordRaw[] {
      return this.menuTabRouters;
    },
    getIsSetPermission(): boolean {
      return this.isSetPermission;
    },
  },
  actions: {
    async generateRoutes() {
      // 获得菜单列表
      let permissionInfo = wsCache.get(CACHE_KEY.PERMISSION);
      if (!permissionInfo) {
        permissionInfo = await getPermissionInfo();
        wsCache.set(CACHE_KEY.PERMISSION, permissionInfo);
      }
      const routerMap: AppRouteRecordRaw[] = generateRoute(
        permissionInfo.menus as AppCustomRouteRecordRaw[],
      );
      // 动态路由，404一定要放到最后面
      this.addRouters = routerMap.concat([
        {
          path: '/:path(.*)*',
          redirect: '/404',
          name: '404Page',
          meta: {
            hidden: true,
            breadcrumb: false,
          },
        },
      ]);
      // 渲染菜单的所有路由
      this.routers = cloneDeep(remainingRouter).concat(routerMap);
      this.isSetPermission = true;
    },
    setMenuTabRouters(routers: AppRouteRecordRaw[]): void {
      this.menuTabRouters = routers;
    },
    removePermission(): void {
      // 删除权限信息
      wsCache.delete(CACHE_KEY.PERMISSION);
      this.resetState();
    },
    resetState() {
      this.isSetPermission = false;
      this.routers = [];
      this.addRouters = [];
      this.menuTabRouters = [];
    },
  },
});

export const usePermissionStoreWithOut = () => {
  return usePermissionStore(store);
};
