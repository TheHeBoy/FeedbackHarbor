import { ApiBase } from "./interface.ts";
import { AxiosInstance, AxiosRequestConfig } from "axios";

// 初始化api请求
export let request: ApiBase;
let axios: AxiosInstance;
let url: String;

// 添加请求路径前缀
async function addPreUrl(option: AxiosRequestConfig, apiUrl?: string) {
  if (apiUrl) {
    option.baseURL = url + apiUrl;
  }
  return axios(option);
}

export const initApi = (axiosInstance: AxiosInstance, baseUrl: String) => {
  axios = axiosInstance;
  url = baseUrl;

  request = {
    get: async (option: AxiosRequestConfig, apiUrl?: string) => {
      const res = await addPreUrl({ method: "GET", ...option }, apiUrl);
      return res.data;
    },
    post: async (option: AxiosRequestConfig, apiUrl?: string) => {
      const res = await addPreUrl({ method: "POST", ...option }, apiUrl);
      return res.data;
    },
    postOriginal: async (option: AxiosRequestConfig, apiUrl?: string) => {
      return await addPreUrl({ method: "POST", ...option }, apiUrl);
    },
    delete: async (option: AxiosRequestConfig, apiUrl?: string) => {
      const res = await addPreUrl({ method: "DELETE", ...option }, apiUrl);
      return res.data;
    },
    put: async (option: AxiosRequestConfig, apiUrl?: string) => {
      const res = await addPreUrl({ method: "PUT", ...option }, apiUrl);
      return res.data;
    },
    download: async (option: AxiosRequestConfig, apiUrl?: string) => {
      return await addPreUrl(
        { method: "GET", responseType: "blob", ...option },
        apiUrl
      );
    },
    upload: async (option: AxiosRequestConfig, apiUrl?: string) => {
      const data = await addPreUrl(
        {
          method: "POST",
          headers: { "Content-Type": "multipart/form-data" },
          ...option,
        },
        apiUrl
      );
      return data.data;
    },
  };
};
