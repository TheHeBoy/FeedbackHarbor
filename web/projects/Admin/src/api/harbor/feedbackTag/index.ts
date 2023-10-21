import request from '@/config/axios';

export interface FeedbackTagVO {
  nameCh: string;
  nameEn: string;
  order: number;
  color: string;
}

// 查询反馈标签列表
export const getFeedbackTagPage = async (params) => {
  return await request.get({ url: `/harbor/feedback-tag/page`, params });
};

// 查询反馈标签详情
export const getFeedbackTag = async (id: number) => {
  return await request.get({ url: `/harbor/feedback-tag/get?id=` + id });
};

// 新增反馈标签
export const createFeedbackTag = async (data: FeedbackTagVO) => {
  return await request.post({ url: `/harbor/feedback-tag/create`, data });
};

// 修改反馈标签
export const updateFeedbackTag = async (data: FeedbackTagVO) => {
  return await request.put({ url: `/harbor/feedback-tag/update`, data });
};

// 删除反馈标签
export const deleteFeedbackTag = async (id: number) => {
  return await request.delete({ url: `/harbor/feedback-tag/delete?id=` + id });
};

// 导出反馈标签 Excel
export const exportFeedbackTag = async (params) => {
  return await request.download({ url: `/harbor/feedback-tag/export-excel`, params });
};
