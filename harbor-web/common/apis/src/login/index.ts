import { request } from '../index';

export interface AuthLoginRespVO {
  userId: number;
  accessToken: string;
  expiresTime: number;
}

export interface MailCaptchaVO {
  username: string;
  password: string;
  mail: string;
  captcha: string;
}

export interface ResetPasswdVO {
  userId: number;
  password: string;
  mail: string;
  captcha: string;
}

export interface UserLoginVO {
  username: string;
  password: string;
}

export interface SocialLoginVO {
  type: number;
  code: string;
  state: string;
  redirectUri: string;
}

// 登录
export const login = (data: UserLoginVO): Promise<AuthLoginRespVO> => {
  return request.post({ url: '/system/auth/login', data }, '/admin-api');
};

// 登出
export const logout = () => {
  return request.post({ url: '/system/auth/logout' }, '/admin-api');
};

// 邮箱注册
export const mailRegister = (data: MailCaptchaVO) => {
  return request.post({ url: '/system/auth/mail-register', data }, '/admin-api');
};

// 发送邮箱验证码
export const sendRegisterMailCaptcha = (mail: String) => {
  return request.post(
    { url: '/system/auth/send-register-mail-captcha', params: { mail } },
    '/admin-api',
  );
};

// 检查用户名是否存在
export const checkUsername = (username: String) => {
  return request.get({ url: '/system/auth/check-username', params: { username } }, '/admin-api');
};

// 发送重置密码验证码
export const sendResetPasswdMailCaptcha = (mail: String) => {
  return request.post(
    { url: '/system/auth/send-reset-passwd-mail-captcha', params: { mail } },
    '/admin-api',
  );
};

export const resetPasswd = (data: ResetPasswdVO) => {
  return request.post({ url: '/system/auth/reset-passwd', data }, '/admin-api');
};

export const socialAuthRedirect = (type: number, redirectUri: string) => {
  return request.get(
    {
      url: '/system/auth/social-auth-redirect?type=' + type + '&redirectUri=' + redirectUri,
    },
    '/admin-api',
  );
};

export const socialLogin = (data: SocialLoginVO) => {
  return request.post({ url: '/system/auth/social-login', data: data }, '/admin-api');
};

export const getUserInfo = () => {
  return request.get({ url: '/system/auth/login-user-info' }, '/admin-api');
};
