import { TokenType } from '@/api/login';
import { CACHE_KEY, useCache } from '@/hooks/useCache';
import { getExp } from './formatTime';

const { wsCache } = useCache();

//------------ token ----------

// 获取AccessToken
export const getAccessToken = () => {
  return wsCache.get(CACHE_KEY.ACCESS_TOKEN);
};

// 设置token
export const setToken = (token: TokenType) => {
  wsCache.set(CACHE_KEY.ACCESS_TOKEN, token.accessToken, { exp: getExp(token.expiresTime) });
};

// 删除token
export const removeToken = () => {
  wsCache.delete(CACHE_KEY.ACCESS_TOKEN);
};

//------------ tenant ----------
