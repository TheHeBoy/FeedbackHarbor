package cn.hh.harbor.module.harbor.controller.admin.home;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.harbor.controller.admin.feedback.vo.FeedbackPageReqVO;
import cn.hh.harbor.module.harbor.controller.admin.feedback.vo.FeedbackRespVO;
import cn.hh.harbor.module.harbor.controller.admin.home.vo.HomeStatisticsReqVO;
import cn.hh.harbor.module.harbor.controller.admin.home.vo.HomeStatisticsRespVO;
import cn.hh.harbor.module.harbor.service.feedback.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Tag(name = "管理后台 - 首页")
@RestController
@RequestMapping("/harbor/home")
@Validated
public class HomeController {

    @Resource
    private FeedbackService feedbackService;

    @Operation(summary = "统计")
    @GetMapping("/statistics")
    public CommonResult<HomeStatisticsRespVO> statistics(@Valid HomeStatisticsReqVO reqVO) {
        // 1. 统计反馈
        PageResult<FeedbackRespVO> feedbackPage = feedbackService.getFeedbackPage(new FeedbackPageReqVO().setCreateTime(reqVO.getCreateTime()));
        Long feedbackTotal = feedbackPage.getTotal();

        return CommonResult.success(new HomeStatisticsRespVO().setFeedbackTotal(feedbackTotal));
    }
}