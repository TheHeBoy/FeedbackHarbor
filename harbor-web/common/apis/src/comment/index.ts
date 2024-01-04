import { PageParam, PageResult, request } from '../index';

interface CommentBase {
  id: number;
  parentId: number | null;
  uid: number;
  feedbackId: number;
  content: string;
  likes: number;
  userType: number;
  avatar: string;
  nickname: string;
  createTime: number;
  imgs?: string[];
}

export interface CommentVO extends CommentBase {
  replyPage?: PageResult<ReplyVO>;
}

export interface ReplyVO extends CommentBase {}

export interface createCommentVO {
  content: string;
  feedbackId: number;
  parentId?: number;
  imgs?: string[];
}

export interface CommentPageParams extends PageParam {
  feedbackId: number;
}

export interface ReplyPageParams extends PageParam {
  commentId: number;
}

export const createComment = (data: createCommentVO) => {
  return request.post({ url: '/comment/create', data: data }, '/app-api/harbor');
};

export const getCommentPage = (data: CommentPageParams): Promise<PageResult<CommentVO>> => {
  return request.get({ url: '/comment/page', params: data }, '/app-api/harbor');
};

export const getReplyPage = (data: ReplyPageParams): Promise<PageResult<ReplyVO>> => {
  return request.get({ url: '/comment/replyPage', params: data }, '/app-api/harbor');
};
