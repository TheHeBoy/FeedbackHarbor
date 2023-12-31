import router from './router';
import type { RouteRecordRaw } from 'vue-router';
import { getAccessToken, getTenant, getTenantLogo } from '@/utils/auth';
import { useTitle } from '@/hooks/web/useTitle';
import { useNProgress } from '@/hooks/web/useNProgress';
import { usePageLoading } from '@/hooks/web/usePageLoading';
import { useDictStoreWithOut } from '@/store/modules/dict';
import { useUserStoreWithOut } from '@/store/modules/user';
import { usePermissionStoreWithOut } from '@/store/modules/permission';
import { changeFavicon } from '@/utils/favicon';

const { start, done } = useNProgress();

const { loadStart, loadDone } = usePageLoading();
// 路由不重定向白名单
const whiteList = ['/login', '/social-login', '/auth-redirect', '/bind', '/register'];
// 租户不重定向白名单
const tenantWhiteList = ['/selectTenant', '/createTenant'];

// 路由加载前
router.beforeEach(async (to, from, next) => {
  start();
  loadStart();
  if (getAccessToken()) {
    // 没有租户信息且不在租户不重定向白名单中
    if (!getTenant() && tenantWhiteList.indexOf(to.path) === -1) {
      next('/selectTenant'); // 定位到租户选择界面
      return;
    }

    if (to.path === '/login') {
      const code = to.query.code;
      // 通过邀请链接进入登录页面时,已经登录了就之间跳转到选择租户界面
      if (code) {
        next({ path: '/selectTenant', query: { code } });
      } else {
        next({ path: '/' });
      }
    } else {
      // 获取所有字典
      const dictStore = useDictStoreWithOut();
      const userStore = useUserStoreWithOut();
      const permissionStore = usePermissionStoreWithOut();

      let nextData: undefined | {};
      if (!dictStore.getIsSetDict) {
        await dictStore.setDictMap();
      }

      // 用户信息
      if (!userStore.getIsSetUser) {
        await userStore.setUserInfoAction();

        // 登录页面需要重定向
        if (from.query.redirect) {
          const redirect = decodeURIComponent(from.query.redirect as string);
          nextData = to.path === redirect ? { ...to, replace: true } : { path: redirect };
        }
      }

      // 菜单权限，租户选择界面不需要菜单权限
      if (tenantWhiteList.indexOf(to.path) === -1 && !permissionStore.getIsSetPermission) {
        await permissionStore.generateRoutes();
        permissionStore.getAddRouters.forEach((route) => {
          router.addRoute(route as unknown as RouteRecordRaw); // 动态添加可访问路由表
        });
        if (!nextData) {
          nextData = { ...to, replace: true };
        }
      }

      if (nextData) {
        // 会再次进入到router.beforeEach方法中, 为了保证动态路由添加成功
        next(nextData);
      } else {
        // 不会再次进入到router.beforeEach方法中，动态路由已添加成功
        next();
      }
    }
  } else if (whiteList.indexOf(to.path) !== -1) {
    next();
  } else {
    next(`/login?redirect=${to.fullPath}`); // 否则全部重定向到登录页
  }

  // 如果有租户信息修改页面图标
  if (getTenantLogo()) {
    changeFavicon(getTenantLogo());
  }
});

router.afterEach((to) => {
  useTitle(to?.meta?.title as string);
  done(); // 结束Progress
  loadDone();
});
