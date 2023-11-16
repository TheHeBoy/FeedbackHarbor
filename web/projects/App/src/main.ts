import { createI18n } from 'vue-i18n';
import messages from '@intlify/vite-plugin-vue-i18n/messages';
import router from '@/router/index';
import store from '@/store';
import App from './App.vue';
import 'virtual:windi.css';
import 'virtual:windi-devtools';
import '@/assets/styles/index.scss';
import { initApi } from '@harbor/apis';
import service from '@/api/http';

const i18n = createI18n({
  locale: 'zh-CN',
  messages,
});

const app = createApp(App);
app.use(router)
app.use(store);
app.use(i18n);
app.mount('#app');

initApi(service);
