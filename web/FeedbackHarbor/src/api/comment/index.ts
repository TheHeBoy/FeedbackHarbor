import request from '@/api/http';

export type createCommentVO = {
  content: string;
  feedbackId: number;
  parentId?: number;
  imgs?: string;
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
