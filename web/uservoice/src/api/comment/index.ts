import request from '@/api/http';

export type createCommentVO = {
  uid: number;
  content: string;
  feedbackId: number;
  userType: number;
  parentId: number | null;
  nickname: string;
  avatar: string;
};

export type FeedbackPageParams = {
  pageNo: number;
  pageSize: number;
  feedbackId: number
};

export const createComment = (data: createCommentVO) => {
  return request.post('/comment/create', data);
};

export const getCommentPage = (data: FeedbackPageParams) => {
  return request.get('/comment/page', data);
};
