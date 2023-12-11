import { createRouter, createWebHistory, Router, RouteRecordRaw } from 'vue-router';
import NProgress from 'nprogress';
import exceptionRoutes from '@/router/route.exception';
import asyncRoutes from '@/router/route.async';
import { useUserStoreWithOut } from '@/store/user';
import { useTenantStoreWithOut } from '@/store/tenant';
import { changeFavicon } from '@harbor/core/src/utils/favicon';

const routes: Array<RouteRecordRaw> = [
  ...asyncRoutes,
  // 异常页必须放在路由匹配规则的最后
  ...exceptionRoutes,
];

const router: Router = createRouter({
  history: createWebHistory(import.meta.env.VITE_BASE),
  routes,
});

const useUser = useUserStoreWithOut();
const useTenant = useTenantStoreWithOut();
// 无需tenantRouterUri的页面
const whiteList = ['/404', '/social-login'];
/**
 * @description: 全局路由前置守卫，在进入路由前触发，导航在所有守卫 resolve 完之前一直处于等待中。
 */
router.beforeEach(async (to, from, next) => {
  // 设置页面标题
  document.title = (to.meta.title as string) || import.meta.env.VITE_APP_TITLE;
  const toPath = to.path;
  if (whiteList.indexOf(toPath) == -1) {
    // 路由路径如果没有二层路径,就不会存在租户路由,路由路径第一层为租户路由
    if (toPath.substring(1, toPath.length - 1).indexOf('/') == -1) {
      if (useTenant.isSetTenant) {
        // 传递租户路由uri
        next(`/${useTenant.tenant.routerUri}${toPath}`);
      } else {
        next('/404');
      }
      return;
    } else {
      let tenantRouterUri = to.params.tenantRouterUri as unknown as string;
      // 设置租户信息并检查租户路由
      if (!useTenant.isSetTenant) {
        await useTenant.setTenantAction(tenantRouterUri);
        // 租户路由错误
        if (!useTenant.isSetTenant) {
          next('/404');
          return;
        }
      }

      // 设置用户信息
      if (!useUser.isSetUser) {
        await useUser.setUserInfoAction();
      }
      if (!NProgress.isStarted()) {
        NProgress.start();
      }
    }
  }

  // 改变页面图标
  if (useTenant.isSetTenant) {
    changeFavicon(useTenant.tenant.logo);
  }
  next();
});

router.afterEach((to, from) => {
  NProgress.done();
});

export default router;
