import { AccessTokenKey } from '../index.ts';
import { getExp } from '../../utils/time.ts';
import { useCache } from '../useCache.ts';

const { wsCache } = useCache();

export const getAccessToken = () => {
  return wsCache.get(AccessTokenKey);
};

// 设置token
export const setToken = (data: any) => {
  wsCache.set(AccessTokenKey, data.accessToken, { exp: getExp(data.expiresTime) });
};

// 删除token
export const removeToken = () => {
  wsCache.delete(AccessTokenKey);
};
