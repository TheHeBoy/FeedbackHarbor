import { CACHE_KEY, useCache } from '@/hooks/web/useCache';
import { decrypt, encrypt } from '@/utils/jsencrypt';
import { changeFavicon } from '@harbor/core/src/utils/favicon';
import { usePermissionStore } from '@/store/modules/permission';
import { TenantKeyType } from '@harbor/core/src/wscache/tenant';

const { wsCache } = useCache();
// ========== Token相关 ==========

export * from '@harbor/core/src/wscache/token';

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
  return getTenant()?.id;
};

export const getTenantLogo = () => {
  return getTenant()?.logo;
};

export const getTenantName = () => {
  return getTenant()?.name;
};

export const getTenantRouterUri = () => {
  return getTenant()?.routerUri;
};

export const setTenant = (type: TenantKeyType) => {
  wsCache.set(TenantKey, type);
  // 修改页面标签图标
  changeFavicon(type.logo);
  // 移除权限
  usePermissionStore().removePermission();
};

export const getTenant = (): TenantKeyType => {
  return wsCache.get(TenantKey);
};
