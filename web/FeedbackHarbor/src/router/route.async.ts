// 需要鉴权的业务路由
import { RouteRecordRaw } from 'vue-router';

const asyncRoutes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    meta: {
      title: '',
      icon: '',
    },
    component: () => import('@/views/home/index.vue'),
  },
  {
    path: '/social-login',
    name: '/social-login',
    meta: {
      title: '',
      icon: '',
    },
    component: () => import('@/views/socialLogin.vue'),
  },
];

export default asyncRoutes;
