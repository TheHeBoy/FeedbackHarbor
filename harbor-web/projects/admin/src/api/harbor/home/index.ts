import { request } from '@harbor/apis';

export interface HomeStatisticsReqVO {
  createTime: any[] | null;
}

// 查询反馈标签列表
export const statistics = async (params: HomeStatisticsReqVO) => {
  return await request.get({ url: `/harbor/home/statistics`, params });
};
