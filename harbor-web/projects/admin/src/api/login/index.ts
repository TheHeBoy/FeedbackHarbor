import { request } from '@harbor/apis';
import { getRefreshToken } from '@/utils/auth';
import type { UserLoginVO } from './types';

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

// 登录
export const login = (data: UserLoginVO) => {
  return request.post({ url: '/system/auth/login', data });
};

// 刷新访问令牌
export const refreshToken = () => {
  return request.post({ url: '/system/auth/refresh-token?refreshToken=' + getRefreshToken() });
};

// 登出
export const loginOut = () => {
  return request.post({ url: '/system/auth/logout' });
};

// 获取用户权限信息
export const getPermissionInfo = () => {
  return request.get({ url: '/system/auth/get-permission-info' });
};

// 获取验证图片以及 token
export const getCode = (data) => {
  return request.postOriginal({ url: 'system/captcha/get', data });
};

// 滑动或者点选验证
export const reqCheck = (data) => {
  return request.postOriginal({ url: 'system/captcha/check', data });
};

// 查询登录用户详情
export const getUserLoginInfo = () => {
  return request.get({ url: '/system/auth/login-user-info' });
};

// 邮箱注册
export const mailRegister = (data: MailCaptchaVO) => {
  return request.post({ url: '/system/auth/mail-register', data });
};

// 发送邮箱验证码
export const sendRegisterMailCaptcha = (mail: String) => {
  return request.post({ url: '/system/auth/send-mail-captcha', params: { mail } });
};

// 检查用户名是否存在
export const checkUsername = (username: String) => {
  return request.get({ url: '/system/auth/check-username', params: { username } });
};

// 发送重置密码验证吗
export const sendResetPasswdMailCaptcha = (mail: String) => {
  return request.post({ url: '/system/auth/send-reset-passwd-mail-captcha', params: { mail } });
};

export const resetPasswd = (data: ResetPasswdVO) => {
  return request.post({ url: '/system/auth/reset-passwd', data });
};
