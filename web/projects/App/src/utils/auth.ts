import { TokenType } from '@/api/login';
import { useCache } from '@/hooks/useCache';
import { getExp } from './formatTime';
import { AccessTokenKey } from '@harbor/core';
import router from '@/router';

const { wsCache } = useCache();

//------------ token ----------

// 获取token
export const getAccessToken = () => {
  return wsCache.get(AccessTokenKey);
};

// 设置token
export const setToken = (token: TokenType) => {
  wsCache.set(AccessTokenKey, token.accessToken, { exp: getExp(token.expiresTime) });
};

// 删除token
export const removeToken = () => {
  wsCache.delete(AccessTokenKey);
};

//------------ tenant ----------

// 系统租户id
const systemTenantId = 1;


