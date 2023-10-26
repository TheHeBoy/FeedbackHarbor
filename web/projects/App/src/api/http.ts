import axios, { AxiosError, AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import showCodeMessage from '@/api/code';
import { getAccessToken } from '@/utils/auth';
import { useloginStoreWithOut } from '@/store/login';
import { ApiBase } from '@harbor/apis';
import { AxiosRequestConfig } from 'axios/index';

// 创建实例
const axiosInstance: AxiosInstance = axios.create({
  // 前缀
  baseURL: import.meta.env.VITE_API_BASEURL + import.meta.env.VITE_API_URL,
  // 超时
  timeout: 1000 * 30,
  // 请求头
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器
axiosInstance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    if (getAccessToken()) {
      config.headers.Authorization = `Bearer ${getAccessToken()}`;
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
      useloginStoreWithOut().open();
      return Promise.reject('未认证，请登录');
    } else if (code == 500) {
      ElMessage.error(showCodeMessage(data.msg));
      return Promise.reject(data.msg);
    } else if (code == 400) {
      ElMessage.error(showCodeMessage(data.msg));
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

async function request(option: AxiosRequestConfig, apiUrl?: string) {
  if (apiUrl) {
    option.baseURL = import.meta.env.VITE_API_BASEURL + apiUrl;
  }
  return await axiosInstance(option);
}

const service: ApiBase = {
  get: async (option: AxiosRequestConfig, apiUrl?: string) => {
    const res = await request({ method: 'GET', ...option }, apiUrl);
    return res.data;
  },
  post: async (option: AxiosRequestConfig, apiUrl?: string) => {
    const res = await request({ method: 'POST', ...option }, apiUrl);
    return res.data;
  },
  postOriginal: async (option: AxiosRequestConfig, apiUrl?: string) => {
    const res = await request({ method: 'POST', ...option }, apiUrl);
    return res;
  },
  delete: async (option: AxiosRequestConfig, apiUrl?: string) => {
    const res = await request({ method: 'DELETE', ...option }, apiUrl);
    return res.data;
  },
  put: async (option: AxiosRequestConfig, apiUrl?: string) => {
    const res = await request({ method: 'PUT', ...option }, apiUrl);
    return res.data;
  },
  download: async (option: AxiosRequestConfig, apiUrl?: string) => {
    return await request({ method: 'GET', responseType: 'blob', ...option }, apiUrl);
  },
  upload: async (option: AxiosRequestConfig, apiUrl?: string) => {
    const data = await request(
      {
        method: 'POST',
        headers: { 'Content-Type': 'multipart/form-data' },
        ...option,
      },
      apiUrl,
    );
    return data.data;
  },
};

export default service;
