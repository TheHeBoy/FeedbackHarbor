import { TokenType } from '@/api/login';
import { useCache } from '@/hooks/useCache';
import { getExp } from './formatTime';

const { wsCache } = useCache();

const AccessTokenKey = 'ACCESS_TOKEN';

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
