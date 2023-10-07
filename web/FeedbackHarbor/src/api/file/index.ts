import request from '@/api/http';

export const uploadFile = (file: FormData) => {
  return request.upload('/file/upload', file);
};

export const uploadFiles = (file: FormData) => {
  return request.upload('/file/uploads', file);
};

