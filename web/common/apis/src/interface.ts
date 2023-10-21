// // 定义 api 请求接口
import { AxiosRequestConfig } from "axios";

export interface ApiBase {
  // apiUrl参数部不为空时会覆盖原有的接口前缀

  get(option: AxiosRequestConfig, apiUrl?: string): Promise<any>;

  post(option: AxiosRequestConfig, apiUrl?: string): Promise<any>;

  postOriginal(option: AxiosRequestConfig, apiUrl?: string): Promise<any>;

  delete(option: AxiosRequestConfig, apiUrl?: string): Promise<any>;

  put(option: AxiosRequestConfig, apiUrl?: string): Promise<any>;

  download(option: AxiosRequestConfig, apiUrl?: string): Promise<any>;

  upload(option: AxiosRequestConfig, apiUrl?: string): Promise<any>;
}
