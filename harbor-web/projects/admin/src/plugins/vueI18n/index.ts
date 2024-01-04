import type { App } from 'vue';
import { createI18n } from 'vue-i18n';
import zhCnMessage from '@/locales/zh-CN';
import enMessage from '@/locales/en';
import { useLocaleStoreWithOut } from '@/store/modules/locale';

const localeStore = useLocaleStoreWithOut();
const locale = localeStore.getCurrentLocale;

const i18n = createI18n({
  locale: locale.lang,
  //https://github.com/quasarframework/quasar/issues/11495
  legacy: false,
  fallbackLocale: 'zh-CN',
  messages: {
    'zh-CN': zhCnMessage,
    en: enMessage,
  },
});

export const setupI18n = async (app: App<Element>) => {
  app.use(i18n);
};

export default i18n;
