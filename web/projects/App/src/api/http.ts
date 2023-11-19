import axios, { AxiosError, AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import showCodeMessage from '@/api/errorCode';
import { getAccessToken } from '@/utils/auth';
import { useLoginStoreWithOut } from '@/store/login';
import { useTenantStoreWithOut } from '@/store/tenant';

// 创建实例
export const axiosInstance: AxiosInstance = axios.create({
  // 前缀
  baseURL: import.meta.env.VITE_API_BASEURL + import.meta.env.VITE_API_URL,
  // 超时
  timeout: 1000 * 30,
  // 请求头
  headers: {
    'Content-Type': 'application/json',
  },
});

const useTenant = useTenantStoreWithOut();

// 请求拦截器
axiosInstance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    if (getAccessToken()) {
      config.headers.Authorization = `Bearer ${getAccessToken()}`;
    }
    // 添加租户id
    if (useTenant.isSetTenant) {
      config.headers['tenant-id'] = useTenant.tenant.id;
    }

    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  },
);

// 响应拦截器
axiosInstance.interceptors.response.use(
  (response: AxiosResponse) => {
    const { data } = response;
    const code = data.code;
    if (code == 401) {
      // 如果未认证，说明可能是访问令牌过期了
      useLoginStoreWithOut().open();
      return Promise.reject('未认证，请登录');
    } else if (code == 500) {
      ElMessage.error(showCodeMessage(data.msg));
      return Promise.reject(data.msg);
    } else if (code == 400) {
      ElMessage.error(showCodeMessage(data.msg));
      return Promise.reject(data.msg);
    } else if (code == 403) {
      // 租户无权访问时
      return Promise.reject(data.msg);
    } else if (code == 0) {
      return data;
    }
  },
  (error: AxiosError) => {
    const { response } = error;
    if (response) {
      ElMessage.error(showCodeMessage(response.status));
      return Promise.reject(response.data);
    }
    ElMessage.warning('网络连接异常,请稍后再试!');
    return Promise.reject(error);
  },
);
