import { TokenType } from '@/api/login';
import { useCache } from '@/hooks/useCache';

const { wsCache } = useCache();

const AccessTokenKey = 'ACCESS_TOKEN';

// 获取token
export const getAccessToken = () => {
  return wsCache.get(AccessTokenKey);
};

// 设置token
export const setToken = (token: TokenType) => {
  wsCache.set(AccessTokenKey, token.accessToken);
};

// 删除token
export const removeToken = () => {
  wsCache.delete(AccessTokenKey);
};
