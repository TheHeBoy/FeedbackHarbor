import { request } from '@harbor/apis';

export interface FeedbackVO {
  content: string;
}

// 查询反馈列表
export const getFeedbackPage = async (params) => {
  return await request.get({ url: `/harbor/feedback/page`, params });
};

// 查询反馈详情
export const getFeedback = async (id: number) => {
  return await request.get({ url: `/harbor/feedback/get?id=` + id });
};

// 新增反馈
export const createFeedback = async (data: FeedbackVO) => {
  return await request.post({ url: `/harbor/feedback/create`, data });
};

// 删除反馈
export const deleteFeedback = async (id: number) => {
  return await request.delete({ url: `/harbor/feedback/delete?id=` + id });
};

// 导出反馈 Excel
export const exportFeedback = async (params) => {
  return await request.download({ url: `/harbor/feedback/export-excel`, params });
};
