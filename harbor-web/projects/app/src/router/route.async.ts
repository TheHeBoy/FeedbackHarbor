import { RouteRecordRaw } from 'vue-router';
import TheHead from '@/layout/TheHead.vue';

const asyncRoutes: Array<RouteRecordRaw> = [
  {
    path: '/:tenantRouterUri',
    component: TheHead,
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import('@/views/main/index.vue'),
      },
    ],
  },
  {
    path: '/social-login',
    component: () => import('@/views/socialLogin.vue'),
  },
];

export default asyncRoutes;
