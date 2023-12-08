import { request } from '@harbor/apis';

export type UserLoginVO = {
  username: string;
  password: string;
};

export const checkTenantRouterUri = (routerUri: String) => {
  return request.get({ url: '/system/auth/check-tenantRouterUri', params: { routerUri } });
};
