import request from '@/api/http';

export type UserLoginVO = {
  username: string;
  password: string;
};

export type TokenType = {
  id: number; // 编号
  accessToken: string; // 访问令牌
  refreshToken: string; // 刷新令牌
  userId: number; // 用户编号
  userType: number; // 用户类型
  clientId: string; // 客户端编号
  expiresTime: number; // 过期时间
};

export const login = (data: UserLoginVO) => {
  return request.post('/auth/login', data);
};

export const getUserInfo = () => {
  return request.get('/auth/get-user-info');
};

export const logout = () => {
  return request.post('/auth/logout');
};
