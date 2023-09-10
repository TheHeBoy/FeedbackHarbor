package cn.iocoder.yudao.module.harbor.controller.app.feedback;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackLikeReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.iocoder.yudao.module.harbor.convert.feedback.FeedbackConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.harbor.service.feedback.FeedbackLikeService;
import cn.iocoder.yudao.module.harbor.service.feedback.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.Set;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "App - 用户点赞")
@RestController
@RequestMapping("/harbor/feedback-like")
@Validated
public class AppFeedbackLikeController {

    @Resource
    private FeedbackLikeService feedbackLikeService;

    @PostMapping("/like")
    @PreAuthenticated
    @Operation(summary = "点赞")
    public CommonResult<Boolean> like(@Valid @RequestBody AppFeedbackLikeReqVO likeReqVO) {
        return success(feedbackLikeService.like(likeReqVO, getLoginUserId()));
    }

    @GetMapping("/list")
    @Operation(summary = "点赞反馈集合")
    public CommonResult<Set<Long>> listByUid() {
        return success(feedbackLikeService.listByUid(getLoginUserId()));
    }
}
