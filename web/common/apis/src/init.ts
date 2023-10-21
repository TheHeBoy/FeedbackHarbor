import { ApiBase } from "./interface.ts";

// 初始化api请求
let request: ApiBase;
export const initApi = (apiBase: ApiBase) => {
  request = apiBase;
};

export { request };
