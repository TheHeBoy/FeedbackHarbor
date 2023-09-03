import request from '@/api/http';

export type FeedbackVO = {
  id: number;
  createTime: Date;
  content: string;
  likes: number;
  feedbackType: number;
  uid: number;
  nickname: string;
  avatar: string;
  userType: number;
};

export type FeedbackPageParams = {
  pageNo: 1;
  pageSize: 10;
};

export type FeedbackCreateVO = {
  content: string;
  feedbackType: number;
  uid: number;
  nickname: string;
  avatar: string;
  userType: number;
};

export const createFeedback = (data: FeedbackCreateVO) => {
  return request.post('/feedback/create', data);
};

export const getFeedbackPage = (data?: FeedbackPageParams) => {
  return request.get('/feedback/page', data);
};
