// 引入windi css
import '@/plugins/windi.css';

// 导入全局的svg图标
import '@/plugins/svgIcon';

// 初始化多语言
import { setupI18n } from '@/plugins/vueI18n';

// 引入状态管理
import { setupStore } from '@/store';

// 全局组件
import { setupGlobCom } from '@/components';

// 引入 element-plus
import { setupElementPlus } from '@/plugins/elementPlus';

// 引入全局样式
import '@/styles/index.scss';

// 引入动画
import '@/plugins/animate.css';

// 路由
import router, { setupRouter } from '@/router';

// 权限
import { setupAuth } from '@/directives';

import { createApp } from 'vue';

import App from './App.vue';

import './permission';

import '@/plugins/tongji'; // 百度统计

import VueDOMPurifyHTML from 'vue-dompurify-html'; // 解决v-html 的安全隐患
import { axiosInstance } from '@/api/http';
import { initApi } from '@harbor/apis';
// 创建实例
const setupAll = async () => {
  const app = createApp(App);

  // 给通用组件提供 api 请求
  initApi(axiosInstance, import.meta.env.VITE_API_BASEURL);

  await setupI18n(app);

  setupStore(app);

  setupGlobCom(app);

  setupElementPlus(app);

  setupRouter(app);

  setupAuth(app);

  await router.isReady();

  app.use(VueDOMPurifyHTML);

  app.mount('#app');
};

await setupAll();
