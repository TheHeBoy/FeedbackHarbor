/**
 * 配置浏览器本地存储的方式，可直接存储对象数组。
 */

import { AccessTokenKey } from '@harbor/core/src/wscache';

export { useCache } from '@harbor/core/src/wscache';

export const CACHE_KEY = {
  USER: 'app_user',
  ACCESS_TOKEN: AccessTokenKey,
  TENANT: 'app_tenant',
};
