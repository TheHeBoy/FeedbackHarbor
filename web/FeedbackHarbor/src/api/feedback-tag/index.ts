import request from '@/api/http';

export type FeedbackTagVO = {
  id: number;
  nameCh: string;
  nameEn: string;
  sort: number;
  color: string;
};

export const getFeedbackTagList = () => {
  return request.get('/feedback-tag/list');
};
