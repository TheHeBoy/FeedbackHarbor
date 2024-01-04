import { request } from '@harbor/apis';
import { FeedbackVO } from '@harbor/apis/src/feedback';

export const getById = (id: number): Promise<FeedbackVO> => {
  return request.get({ url: '/harbor/feedback/get', params: { id: id } });
};
