/**
 * 配置浏览器本地存储的方式，可直接存储对象数组。
 */
import { AccessTokenKey } from '@harbor/core/src/wscache';
import WebStorageCache from 'web-storage-cache';

type CacheType = 'localStorage' | 'sessionStorage';

export const CACHE_KEY = {
  IS_DARK: 'isDark',
  USER: 'user',
  LANG: 'lang',
  THEME: 'theme',
  LAYOUT: 'layout',
  PERMISSION: 'permission',
  DICT_CACHE: 'dictCache',
  REFRESH_TOKEN: 'refresh_token',
  ACCESS_TOKEN: AccessTokenKey,
  LOGIN_FORM: 'login_form',
  TENANT: 'tenant_key',
};

export const useCache = (type: CacheType = 'localStorage') => {
  const wsCache: WebStorageCache = new WebStorageCache({
    storage: type,
  });

  return {
    wsCache,
  };
};
