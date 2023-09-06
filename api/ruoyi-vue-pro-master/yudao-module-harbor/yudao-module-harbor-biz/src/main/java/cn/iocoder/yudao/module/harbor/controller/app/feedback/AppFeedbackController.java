package cn.iocoder.yudao.module.harbor.controller.app.feedback;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.iocoder.yudao.module.harbor.convert.feedback.FeedbackConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.harbor.service.feedback.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

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
    public CommonResult<FeedbackDO> createFeedback(@Valid @RequestBody AppFeedbackCreateReqVO createReqVO) {
        return success(feedbackService.createFeedback(createReqVO));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户反馈分页")
    public CommonResult<PageResult<AppFeedbackRespVO>> getFeedbackPage(@Valid AppFeedbackPageReqVO pageVO) {
        PageResult<FeedbackDO> pageResult = feedbackService.getFeedbackPage(pageVO);
        return success(FeedbackConvert.INSTANCE.convertPageApp(pageResult));
    }
}
