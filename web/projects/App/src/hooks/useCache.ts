/**
 * 配置浏览器本地存储的方式，可直接存储对象数组。
 */

import WebStorageCache from 'web-storage-cache';
import { AccessTokenKey } from '@harbor/core';

export const CACHE_KEY = {
  USER: 'app_user',
  ACCESS_TOKEN: AccessTokenKey,
  TENANT: 'app_tenant',
};



type CacheType = 'localStorage' | 'sessionStorage';
export const useCache = (type: CacheType = 'localStorage') => {
  const wsCache: WebStorageCache = new WebStorageCache({
    storage: type,
  });

  return {
    wsCache,
  };
};
