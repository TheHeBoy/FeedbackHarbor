import request from '@/api/http';

export type FeedbackLikeVO = {
  feedbackId: number;
};


export const feedbackLike = (data: FeedbackLikeVO) => {
  return request.post('/feedback-like/like', data);
};

export const getFeedbackLikeList = () => {
  return request.get('/feedback-like/list');
};