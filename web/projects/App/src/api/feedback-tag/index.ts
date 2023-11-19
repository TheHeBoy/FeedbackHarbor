import { request } from '@harbor/apis';

export type FeedbackTagVO = {
  id: number;
  nameCh: string;
  nameEn: string;
  sort: number;
  color: string;
};

export const getFeedbackTagList = () => {
  return request.get({ url: '/harbor/feedback-tag/list' });
};
