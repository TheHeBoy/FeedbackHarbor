import { CACHE_KEY, useCache } from '@/hooks/useCache';
import { getExp } from './formatTime';
import { AuthLoginRespVO } from '@harbor/apis/src/login';

const { wsCache } = useCache();

//------------ token ----------

// 获取AccessToken
export const getAccessToken = () => {
  return wsCache.get(CACHE_KEY.ACCESS_TOKEN);
};

// 设置token
export const setToken = (respVO: AuthLoginRespVO) => {
  wsCache.set(CACHE_KEY.ACCESS_TOKEN, respVO.accessToken, { exp: getExp(respVO.expiresTime) });
};

// 删除token
export const removeToken = () => {
  wsCache.delete(CACHE_KEY.ACCESS_TOKEN);
};
