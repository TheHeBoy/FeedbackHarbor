import axios, { AxiosError, AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import showCodeMessage from '@/api/code';
import { formatJsonToUrlParams, instanceObject } from '@/utils/format';
import { getAccessToken } from '@/utils/auth';
import { useloginStoreWithOut } from '@/store/login';

const BASE_PREFIX = import.meta.env.VITE_API_BASEURL;
// 请求白名单，无须token的接口
const whiteList: string[] = ['/login'];
// 创建实例
const axiosInstance: AxiosInstance = axios.create({
  // 前缀
  baseURL: BASE_PREFIX,
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
    let isToken = true;
    whiteList.some((v) => {
      if (config.url) {
        if (config.url.indexOf(v) > -1) {
          isToken = false;
          return true;
        }
      }
      return false;
    });
    if (getAccessToken() && isToken) {
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
    const { data } = response
    const code = data.code;
    if (code == 401) {
      // 如果未认证，说明可能是访问令牌过期了
      useloginStoreWithOut().open()
    } else if (code == 500) {
      ElMessage.error(showCodeMessage(data.msg));
      return Promise.reject(data.msg)
    } else if (code == 0) {
      return data.data;
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
const service = {
  get<T = any>(url: string, data?: object): Promise<T> {
    return axiosInstance.get(url, { params: data });
  },

  post<T = any>(url: string, data?: object): Promise<T> {
    return axiosInstance.post(url, data);
  },

  put<T = any>(url: string, data?: object): Promise<T> {
    return axiosInstance.put(url, data);
  },

  delete<T = any>(url: string, data?: object): Promise<T> {
    return axiosInstance.delete(url, data);
  },

  upload: (url: string, file: FormData | File) =>
    axiosInstance.post(url, file, {
      headers: { 'Content-Type': 'multipart/form-data' },
    }),
  download: (url: string, data: instanceObject) => {
    window.location.href = `${BASE_PREFIX}/${url}?${formatJsonToUrlParams(data)}`;
  },
};

export default service;
