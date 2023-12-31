package cn.hh.harbor.module.harbor.controller.admin.feedback;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.common.pojo.CommonResult;

import static cn.hh.harbor.framework.common.pojo.CommonResult.success;

import cn.hh.harbor.framework.excel.core.util.ExcelUtils;

import cn.hh.harbor.framework.operatelog.core.annotations.OperateLog;

import static cn.hh.harbor.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.hh.harbor.module.harbor.controller.admin.feedback.vo.*;
import cn.hh.harbor.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.hh.harbor.module.harbor.convert.feedback.FeedbackConvert;
import cn.hh.harbor.module.harbor.service.feedback.FeedbackService;

@Tag(name = "管理后台 - 用户反馈")
@RestController
@RequestMapping("/harbor/feedback")
@Validated
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户反馈")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('harbor:feedback:delete')")
    public CommonResult<Boolean> deleteFeedback(@RequestParam("id") Long id) {
        feedbackService.deleteFeedback(id);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户反馈分页")
    @PreAuthorize("@ss.hasPermission('harbor:feedback:query')")
    public CommonResult<PageResult<FeedbackRespVO>> getFeedbackPage(@Valid FeedbackPageReqVO pageVO) {
        return success(feedbackService.getFeedbackPage(pageVO));
    }
}
