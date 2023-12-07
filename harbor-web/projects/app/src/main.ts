import { createI18n } from 'vue-i18n';
import router from '@/router/index';
import store from '@/store';
import App from './App.vue';
import 'virtual:windi.css';
import 'virtual:windi-devtools';
import '@/assets/styles/index.scss';
import { initApi } from '@harbor/apis';
import { axiosInstance } from '@/api/http';
import zhCnMessage from './locales/zh-CN';
import enMessage from './locales/en';

// 国际化
const i18n = createI18n({
  locale: 'zh-CN',
  legacy: false,
  fallbackLocale: 'zh-CN',
  messages: {
    'zh-CN': zhCnMessage,
    en: enMessage,
  },
});

// 提供 api 服务给通用组件使用
initApi(axiosInstance, import.meta.env.VITE_API_BASEURL);

const app = createApp(App);
app.use(router);
app.use(store);
app.use(i18n);
app.mount('#app');
