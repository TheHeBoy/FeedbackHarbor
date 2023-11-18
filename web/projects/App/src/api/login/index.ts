import request from '@/api/http';

export type UserLoginVO = {
  username: string;
  password: string;
};

export type TokenType = {
  accessToken: string; // 访问令牌
  userId: number; // 用户编号
  expiresTime: number; // 过期时间
  tenantId: number;
};

export type SocialLoginVO = {
  type: number;
  code: string;
  state: string;
};

export const login = (data: UserLoginVO) => {
  return request.post({ url: '/system/auth/login', data: data });
};

export const socialAuthRedirect = (type: number, redirectUri: string) => {
  return request.get({
    url: '/system/auth/social-auth-redirect?type=' + type + '&redirectUri=' + redirectUri,
  });
};

export const socialLogin = (data: SocialLoginVO) => {
  return request.post({ url: '/system/auth/social-login', data: data });
};

export const getUserInfo = () => {
  return request.get({ url: '/system/auth/get-user-info' });
};

export const logout = () => {
  return request.post({ url: '/system/auth/logout' });
};

export const checkTenantRouterUri = (routerUri: String) => {
  return request.get({ url: '/system/auth/check-tenantRouterUri', params: { routerUri } });
};
