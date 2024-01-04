import type { App } from 'vue';
import type { RouteRecordRaw } from 'vue-router';
import { createRouter, createWebHistory } from 'vue-router';
import remainingRouter from './modules/remaining';

// 创建路由实例
const router = createRouter({
  history: createWebHistory(import.meta.env.VITE_BASE), // createWebHashHistory URL带#，createWebHistory URL不带#
  strict: true,
  routes: remainingRouter as RouteRecordRaw[],
  scrollBehavior: () => ({ left: 0, top: 0 }),
});

export const setupRouter = (app: App<Element>) => {
  app.use(router);
};

export default router;
