package cn.iocoder.yudao.module.harbor.controller.app.feedback;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.iocoder.yudao.module.harbor.convert.feedback.FeedbackConvert;
import cn.iocoder.yudao.module.harbor.convert.feedbacktag.FeedbackTagConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import cn.iocoder.yudao.module.harbor.service.feedback.FeedbackService;
import cn.iocoder.yudao.module.harbor.service.feedbacktag.FeedbackTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


import java.util.ArrayList;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "App - 用户反馈")
@RestController
@RequestMapping("/harbor/feedback")
@Validated
public class AppFeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private FeedbackTagService feedbackTagService;

    @Resource
    private FeedbackTagConvert feedbackTagConvert;

    @PostMapping("/create")
    @PreAuthenticated
    @Operation(summary = "创建用户反馈")
    public CommonResult<AppFeedbackRespVO> createFeedback(@Valid @RequestBody AppFeedbackCreateReqVO createReqVO) {
        FeedbackDO feedbackDO = feedbackService.createFeedback(createReqVO, getLoginUserId());
        AppFeedbackRespVO feedbackRespVO = FeedbackConvert.INSTANCE.convertPageApp(feedbackDO);
        FeedbackTagDO feedbackTag = feedbackTagService.getFeedbackTag(feedbackDO.getFeedbackTagId());
        feedbackRespVO.setFeedbackTag(feedbackTagConvert.convertApp(feedbackTag));
        return success(feedbackRespVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户反馈分页")
    public CommonResult<PageResult<AppFeedbackRespVO>> getFeedbackPage(@Valid AppFeedbackPageReqVO pageVO) {
        return success(feedbackService.getFeedbackPage(pageVO));
    }
}
