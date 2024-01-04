package cn.hh.harbor.module.harbor.controller.app.serach;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.tenant.core.context.TenantContextHolder;
import cn.hh.harbor.module.harbor.controller.app.serach.vo.SearchPageReqVO;
import cn.hh.harbor.module.harbor.controller.app.serach.vo.SearchPageRespVO;
import cn.hh.harbor.module.harbor.service.search.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.hh.harbor.framework.common.pojo.CommonResult.success;

/**
 * 搜索功能
 *
 * @author hehong
 * @date 2023-12-24
 */
@Tag(name = "App - 搜索")
@RestController
@RequestMapping("/harbor/search")
@Validated
public class SearchController {
    @Resource
    private SearchService searchService;

    @GetMapping("/page")
    @Operation(summary = "搜索分页")
    public CommonResult<PageResult<SearchPageRespVO>> searchPage(@Valid SearchPageReqVO pageVO) {
        searchService.search(pageVO, TenantContextHolder.getTenantId());
        return success(searchService.search(pageVO, TenantContextHolder.getTenantId()));
    }

}
