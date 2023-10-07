import request from '@/api/http';
import { FeedbackTagVO } from '../feedback-tag';

export type FeedbackVO = {
  id: number;
  createTime: Date;
  content: string;
  likes: number;
  feedbackTag: FeedbackTagVO;
  uid: number;
  nickname: string;
  avatar: string;
  userType: number;
  commentNum: number;
  imgs?: string;
};

export type FeedbackPageParams = {
  pageNo: 1;
  pageSize: 10;
};

export type FeedbackCreateVO = {
  content: string;
  feedbackTagId: number;
  imgs?: string;
};

export const createFeedback = (data: FeedbackCreateVO) => {
  return request.post('/feedback/create', data);
};

export const getFeedbackPage = (data?: FeedbackPageParams) => {
  return request.get('/feedback/page', data);
};
