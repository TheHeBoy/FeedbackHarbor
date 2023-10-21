import { request } from "../index";

export const uploadFile = (file: FormData) => {
  return request.upload({ url: "/file/upload", data: file },'/app-api/harbor');
};

export const uploadFiles = (file: FormData) => {
  return request.upload({ url: "/file/uploads", data: file },'/app-api/harbor');
};
