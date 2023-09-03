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
    path: '/test',
    name: 'test',
    meta: {
      title: '',
      icon: '',
    },
    component: () => import('@/views/home/index copy.vue'),
  },
];

export default asyncRoutes;
