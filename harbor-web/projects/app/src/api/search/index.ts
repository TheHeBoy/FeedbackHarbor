import { PageParam, PageResult, request } from '@harbor/apis';

export interface SearchPageRespVO {
  rid: number;
  highContent: string[];
}

export interface SearchPageReqVO extends PageParam {
  searchWords: string;
}

export const searchPage = (params: SearchPageReqVO): Promise<PageResult<SearchPageRespVO>> => {
  return request.get({ url: '/harbor/search/page', params });
};
