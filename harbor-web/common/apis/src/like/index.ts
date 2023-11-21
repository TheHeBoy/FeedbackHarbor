import { request } from "../index";

export type LikeVO = {
  rid: number; // 业务关联id
  busType: number; // 反馈 或 评论 点赞
};

export const like = (data: LikeVO) => {
  return request.post({ url: "/like/like", data: data }, "/app-api/harbor");
};

export const getLikeList = (busType: number) => {
  return request.get(
    { url: "/like/list", params: { busType: busType } },
    "/app-api/harbor"
  );
};
