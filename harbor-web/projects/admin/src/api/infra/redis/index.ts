import { request } from '@harbor/apis';

/**
 * 获取redis 监控信息
 */
export const getCache = () => {
  return request.get({ url: '/infra/redis/get-monitor-info' });
};
