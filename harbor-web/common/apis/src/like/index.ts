import { request } from '../index';

export type LikeVO = {
  rid: number; // 业务关联id
  busType: BusTypeVO; // 反馈 或 评论 点赞
};

export enum BusTypeVO {
  Feedback,
  Comment,
}

export const like = (data: LikeVO) => {
  return request.post({ url: '/like/like', data: data }, '/app-api/harbor');
};

export const getLikeList = (busType: BusTypeVO) => {
  return request.get({ url: '/like/list', params: { busType: busType } }, '/app-api/harbor');
};
