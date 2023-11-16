import { RouteRecordRaw } from 'vue-router';
import TheHead from '@/layout/TheHead.vue';

const asyncRoutes: Array<RouteRecordRaw> = [
  {
    path: '/:tenantRouterUri',
    component: TheHead,
    children: [
      {
        path: '',
        name: 'home',
        component: () => import('@/views/main/index.vue'),
      },
      {
        path: 'roadmap',
        name: 'roadmap',
        component: () => import('@/views/roadmap/index.vue'),
      },
    ],
  },
  {
    path: '/:tenantRouterUri/social-login',
    component: () => import('@/views/socialLogin.vue'),
  },
];

export default asyncRoutes;
