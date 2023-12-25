package cn.hh.harbor.module.harbor.service.search;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.harbor.controller.app.serach.vo.SearchPageReqVO;
import cn.hh.harbor.module.harbor.controller.app.serach.vo.SearchPageRespVO;

/**
 * 搜索服务
 *
 * @author hehong
 * @date 2023-12-23
 */
public interface SearchService {

    PageResult<SearchPageRespVO> search(SearchPageReqVO searchPageReqVO, Long tenantId);
}
