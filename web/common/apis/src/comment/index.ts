import { request } from "../index";

export type createCommentVO = {
  content: string;
  feedbackId: number;
  parentId?: number;
  imgs?: string;
};

export type CommentPageParams = {
  pageNo: number;
  pageSize: number;
  feedbackId: number;
};

export type ReplyPageParams = {
  pageNo: number;
  pageSize: number;
  commentId: number;
};

export const createComment = (data: createCommentVO) => {
  return request.post({ url: "/comment/create", data: data },'/app-api/harbor');
};

export const getCommentPage = (data: CommentPageParams) => {
  return request.get({ url: "/comment/page", params: data },'/app-api/harbor');
};

export const getReplyPage = (data: ReplyPageParams) => {
  return request.get({ url: "/comment/replyPage", params: data },'/app-api/harbor');
};
