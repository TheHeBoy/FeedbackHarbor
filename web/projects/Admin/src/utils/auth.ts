import { CACHE_KEY, useCache } from '@/hooks/web/useCache';
import { TokenType } from '@/api/login/types';
import { decrypt, encrypt } from '@/utils/jsencrypt';
import { TenantKeyType } from '@harbor/core';

const { wsCache } = useCache();
// ========== Token相关 ==========
const AccessTokenKey = CACHE_KEY.ACCESS_TOKEN;
const RefreshTokenKey = CACHE_KEY.REFRESH_TOKEN;

// 获取token
export const getAccessToken = () => {
  return wsCache.get(AccessTokenKey);
};

// 刷新token
export const getRefreshToken = () => {
  return wsCache.get(RefreshTokenKey);
};

// 设置token
export const setToken = (token: TokenType) => {
  wsCache.set(RefreshTokenKey, token.refreshToken);
  wsCache.set(AccessTokenKey, token.accessToken);
};

// 删除token
export const removeToken = () => {
  wsCache.delete(AccessTokenKey);
  wsCache.delete(RefreshTokenKey);
};

// ========== 账号相关 ==========

const LoginFormKey = CACHE_KEY.LOGIN_FORM;

export type LoginFormType = {
  username: string;
  password: string;
  rememberMe: boolean;
};

export const getLoginForm = () => {
  const loginForm: LoginFormType = wsCache.get(LoginFormKey);
  if (loginForm) {
    loginForm.password = decrypt(loginForm.password) as string;
  }
  return loginForm;
};

export const setLoginForm = (loginForm: LoginFormType) => {
  loginForm.password = encrypt(loginForm.password) as string;
  wsCache.set(LoginFormKey, loginForm, { exp: 30 * 24 * 60 * 60 });
};

export const removeLoginForm = () => {
  wsCache.delete(LoginFormKey);
};

// ========== 租户相关 ==========

const TenantKey = CACHE_KEY.TENANT;

export const getTenantId = () => {
  return wsCache.get(TenantKey)?.id;
};

export const getTenantLogo = () => {
  return wsCache.get(TenantKey)?.img;
};

export const getTenantName = () => {
  return wsCache.get(TenantKey)?.name;
};

export const setTenant = (type: TenantKeyType) => {
  wsCache.set(TenantKey, type);
};

export const getTenant = (): TenantKeyType => {
  return wsCache.get(TenantKey);
};

export const removeTenantId = () => {
  wsCache.delete(TenantKey);
};
