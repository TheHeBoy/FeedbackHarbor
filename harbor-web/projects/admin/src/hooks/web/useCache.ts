/**
 * 配置浏览器本地存储的方式，可直接存储对象数组。
 */
import { AccessTokenKey } from '@harbor/core/src/wscache';

export { useCache } from '@harbor/core/src/wscache';
export const CACHE_KEY = {
  IS_DARK: 'isDark',
  USER: 'user',
  LANG: 'lang',
  THEME: 'theme',
  LAYOUT: 'layout',
  PERMISSION: 'permission',
  DICT_CACHE: 'dictCache',
  ACCESS_TOKEN: AccessTokenKey,
  LOGIN_FORM: 'login_form',
  TENANT: 'tenant_key',
};
