package cn.hh.harbor.module.harbor.controller.app.feedback;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.security.core.annotations.PreAuthenticated;
import cn.hh.harbor.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.hh.harbor.module.harbor.controller.app.feedback.vo.AppFeedbackCreateRespVO;
import cn.hh.harbor.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.hh.harbor.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.hh.harbor.module.harbor.convert.feedback.FeedbackConvert;
import cn.hh.harbor.module.harbor.convert.feedbacktag.FeedbackTagConvert;
import cn.hh.harbor.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.hh.harbor.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import cn.hh.harbor.module.harbor.service.feedback.FeedbackService;
import cn.hh.harbor.module.harbor.service.feedbacktag.FeedbackTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


import java.util.ArrayList;

import static cn.hh.harbor.framework.common.pojo.CommonResult.success;
import static cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "App - 用户反馈")
@RestController
@RequestMapping("/harbor/feedback")
@Validated
public class AppFeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @PostMapping("/create")
    @PreAuthenticated
    @Operation(summary = "创建用户反馈")
    public CommonResult<AppFeedbackCreateRespVO> createFeedback(@Valid @RequestBody AppFeedbackCreateReqVO createReqVO) {
        return success(feedbackService.createFeedback(createReqVO, getLoginUserId()));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户反馈分页")
    public CommonResult<PageResult<AppFeedbackRespVO>> getFeedbackPage(@Valid AppFeedbackPageReqVO pageVO) {
        return success(feedbackService.getFeedbackPage(pageVO));
    }
}
