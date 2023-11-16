import { createRouter, createWebHistory, Router, RouteRecordRaw } from 'vue-router';
import NProgress from 'nprogress';
import exceptionRoutes from '@/router/route.exception';
import asyncRoutes from '@/router/route.async';
import { useUserStoreWithOut } from '@/store/user';
import { useTenantStoreWithOut } from '@/store/tenant';

const routes: Array<RouteRecordRaw> = [
  // 带鉴权的业务路由
  ...asyncRoutes,
  // 异常页必须放在路由匹配规则的最后
  ...exceptionRoutes,
];

const router: Router = createRouter({
  history: createWebHistory(import.meta.env.VITE_BASE),
  strict: true,
  routes,
});

const useUser = useUserStoreWithOut();
const useTenant = useTenantStoreWithOut();
/**
 * @description: 全局路由前置守卫，在进入路由前触发，导航在所有守卫 resolve 完之前一直处于等待中。
 */
router.beforeEach(async (to, from, next) => {
  // 设置页面标题
  document.title = (to.meta.title as string) || import.meta.env.VITE_APP_TITLE;

  const tenantRouterUri = to.params.tenantRouterUri as unknown as string;
  console.log(tenantRouterUri);
  // 设置租户信息
  if (!useTenant.isSetTenant) {
    await useTenant.setTenantAction(tenantRouterUri);
    if (!useTenant.tenant) {
      next('/404');
    }
  }
  // 设置用户信息
  if (!useUser.isSetUser) {
    await useUser.setUserInfoAction();
  }
  if (!NProgress.isStarted()) {
    NProgress.start();
  }
  next();
});

router.afterEach((to, from) => {
  NProgress.done();
});

export default router;
