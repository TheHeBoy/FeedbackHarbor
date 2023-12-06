import { request } from "../index";

export type FeedbackVO = {
  id: number;
  createTime: number;
  content: string;
  likes: number;
  feedbackTag: FeedbackTagVO;
  uid: number;
  nickname: string;
  avatar: string;
  userType: number;
  commentNum: number;
  sensitive: string[];
  imgs?: string[];
};

export type FeedbackTagVO = {
  id: number;
  nameCh: string;
  nameEn: string;
  sort: number;
  color: string;
};

export type FeedbackPageParams = {
  pageNo: number;
  pageSize: number;
  order: number;
};

export type FeedbackCreateVO = {
  content: string;
  feedbackTagId: number;
  imgs?: string[];
};

export const createFeedback = (data: FeedbackCreateVO) => {
  return request.post(
    { url: "/feedback/create", data: data },
    "/app-api/harbor"
  );
};

export const getFeedbackPage = (data: FeedbackPageParams) => {
  return request.get(
    { url: "/feedback/page", params: data },
    "/app-api/harbor"
  );
};
