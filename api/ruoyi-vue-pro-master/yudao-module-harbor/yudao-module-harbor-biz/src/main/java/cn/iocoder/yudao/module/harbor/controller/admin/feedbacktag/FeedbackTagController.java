package cn.iocoder.yudao.module.harbor.controller.admin.feedbacktag;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;

import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.harbor.controller.admin.feedbacktag.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import cn.iocoder.yudao.module.harbor.convert.feedbacktag.FeedbackTagConvert;
import cn.iocoder.yudao.module.harbor.service.feedbacktag.FeedbackTagService;

@Tag(name = "管理后台 - 反馈标签")
@RestController
@RequestMapping("/harbor/feedback-tag")
@Validated
public class FeedbackTagController {

    @Resource
    private FeedbackTagService feedbackTagService;

    @PostMapping("/create")
    @Operation(summary = "创建反馈标签")
    @PreAuthorize("@ss.hasPermission('harbor:feedback-tag:create')")
    public CommonResult<Long> createFeedbackTag(@Valid @RequestBody FeedbackTagCreateReqVO createReqVO) {
        return success(feedbackTagService.createFeedbackTag(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新反馈标签")
    @PreAuthorize("@ss.hasPermission('harbor:feedback-tag:update')")
    public CommonResult<Boolean> updateFeedbackTag(@Valid @RequestBody FeedbackTagUpdateReqVO updateReqVO) {
        feedbackTagService.updateFeedbackTag(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除反馈标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('harbor:feedback-tag:delete')")
    public CommonResult<Boolean> deleteFeedbackTag(@RequestParam("id") Long id) {
        feedbackTagService.deleteFeedbackTag(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得反馈标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('harbor:feedback-tag:query')")
    public CommonResult<FeedbackTagRespVO> getFeedbackTag(@RequestParam("id") Long id) {
        FeedbackTagDO feedbackTag = feedbackTagService.getFeedbackTag(id);
        return success(FeedbackTagConvert.INSTANCE.convert(feedbackTag));
    }

    @GetMapping("/list")
    @Operation(summary = "获得反馈标签列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('harbor:feedback-tag:query')")
    public CommonResult<List<FeedbackTagRespVO>> getFeedbackTagList(@RequestParam("ids") Collection<Long> ids) {
        List<FeedbackTagDO> list = feedbackTagService.getFeedbackTagList(ids);
        return success(FeedbackTagConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得反馈标签分页")
    @PreAuthorize("@ss.hasPermission('harbor:feedback-tag:query')")
    public CommonResult<PageResult<FeedbackTagRespVO>> getFeedbackTagPage(@Valid FeedbackTagPageReqVO pageVO) {
        PageResult<FeedbackTagDO> pageResult = feedbackTagService.getFeedbackTagPage(pageVO);
        return success(FeedbackTagConvert.INSTANCE.convertPage(pageResult));
    }
}
