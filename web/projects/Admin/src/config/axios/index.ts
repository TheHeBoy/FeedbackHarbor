import { service } from './service';
import { ApiBase } from '@harbor/apis';
import { AxiosRequestConfig } from 'axios';

// 添加请求路径前缀
async function request(option: AxiosRequestConfig, apiUrl?: string) {
  if (apiUrl) {
    option.baseURL = import.meta.env.VITE_BASE_URL + apiUrl;
  }
  return service(option);
}

export default {
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
} as ApiBase;
