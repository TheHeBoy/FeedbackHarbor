import { request } from '@harbor/apis';

// 获取用户权限信息
export const getPermissionInfo = () => {
  return request.get({ url: '/system/auth/get-permission-info' });
};
