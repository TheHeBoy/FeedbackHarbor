import { request } from "../index";

export type LikeVO = {
  rid: number;
  busType: number;
};

export const like = (data: LikeVO) => {
  return request.post({ url: "/like/like", data: data }, "/app-api/harbor");
};

export const getLikeList = (data: number) => {
  return request.get(
    { url: "/like/list", params: { busType: data } },
    "/app-api/harbor"
  );
};
