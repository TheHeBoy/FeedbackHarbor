// 需要鉴权的业务路由
import { RouteRecordRaw } from 'vue-router';

const asyncRoutes: Array<RouteRecordRaw> = [
  {
    path: '/:productId?',
    name: 'main',
    component: () => import('@/views/main/index.vue'),
  },
  {
    path: '/:productId?/roadmap',
    name: 'roadmap',
    component: () => import('@/views/roadmap/index.vue'),
  },
  {
    path: '/social-login',
    name: '/social-login',
    component: () => import('@/views/socialLogin.vue'),
  },
];

export default asyncRoutes;
