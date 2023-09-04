// 跟鉴权无关的特殊路由
import { RouteRecordRaw } from 'vue-router';

const exceptionRoutes: Array<RouteRecordRaw> = [
  {
    path: '/404',
    name: '404',
    meta: {
      title: '非常抱歉,页面走丢了',
    },
    component: () => import('@/views/exception/404.vue'),
  },
  {
    path: '/:pathMatch(.*)',
    meta: {},
    redirect: '/404',
  },
];

export default exceptionRoutes;
