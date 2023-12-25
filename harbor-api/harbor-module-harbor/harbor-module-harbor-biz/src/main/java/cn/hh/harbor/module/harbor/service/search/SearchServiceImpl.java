package cn.hh.harbor.module.harbor.service.search;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.common.util.object.PageUtils;
import cn.hh.harbor.module.harbor.controller.app.serach.vo.SearchPageReqVO;
import cn.hh.harbor.module.harbor.controller.app.serach.vo.SearchPageRespVO;
import cn.hh.harbor.module.harbor.dal.dataobject.feedback.FeedbackES;
import cn.hh.harbor.module.harbor.dal.es.feedback.FeedbackRepository;
import cn.hutool.core.collection.CollUtil;
import co.elastic.clients.elasticsearch.ml.Page;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

/**
 * 搜索服务实现
 *
 * @author hehong
 * @date 2023-12-23
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public PageResult<SearchPageRespVO> search(SearchPageReqVO reqVO, Long tenantId) {

        // 构建搜索条件
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("content", reqVO.getSearchWords()))
                        .must(QueryBuilders.termQuery("tenantId", tenantId)))
                .withHighlightBuilder(new HighlightBuilder().field("content").requireFieldMatch(false))
                // 初始页数为0
                .withPageable(PageRequest.of(reqVO.getPageNo() - 1, reqVO.getPageSize(), Sort.by(Sort.Direction.DESC, "id")))
                .build();

        SearchHits<FeedbackES> searchHits = elasticsearchRestTemplate.search(query, FeedbackES.class);

        // 解析搜索结果
        List<SearchPageRespVO> searchPageRespVOS = new ArrayList<>();

        for (SearchHit<FeedbackES> hit : searchHits.getSearchHits()) {
            SearchPageRespVO respVO = new SearchPageRespVO();

            // 获取文档source
            FeedbackES feedbackES = hit.getContent();

            // 获取高亮结果
            List<String> highContent = hit.getHighlightField("content");
            respVO.setRid(feedbackES.getId()).setHighContent(highContent);

            searchPageRespVOS.add(respVO);
        }


        return new PageResult<>(searchPageRespVOS, searchHits.getTotalHits());
    }
}
