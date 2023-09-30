import request from '@/api/http';

export type LikeVO = {
  rid: number;
  busType: number;
};

export const like = (data: LikeVO) => {
  return request.post('/like/like', data);
};

export const getLikeList = (data: number) => {
  return request.get('/like/list', { busType: data });
};
