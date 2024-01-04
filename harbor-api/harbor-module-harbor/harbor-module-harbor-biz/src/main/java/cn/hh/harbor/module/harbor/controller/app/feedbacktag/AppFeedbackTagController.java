package cn.hh.harbor.module.harbor.controller.app.feedbacktag;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.security.core.annotations.PreAuthenticated;
import cn.hh.harbor.module.harbor.controller.app.feedbacktag.vo.AppFeedbackTagRespVO;
import cn.hh.harbor.module.harbor.convert.feedbacktag.FeedbackTagConvert;
import cn.hh.harbor.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import cn.hh.harbor.module.harbor.service.feedbacktag.FeedbackTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static cn.hh.harbor.framework.common.pojo.CommonResult.success;
import static cn.hh.harbor.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "App - 反馈标签")
@RestController
@RequestMapping("/harbor/feedback-tag")
@Validated
public class AppFeedbackTagController {

    @Resource
    private FeedbackTagService feedbackTagService;

    @GetMapping("/list")
    @PermitAll
    @Operation(summary = "获得所有反馈标签列表")
    public CommonResult<List<AppFeedbackTagRespVO>> getFeedbackTagList() {
        List<FeedbackTagDO> list = feedbackTagService.getFeedbackTagList();
        list.sort(Comparator.comparing(FeedbackTagDO::getSort));
        return success(FeedbackTagConvert.INSTANCE.convertListApp(list));
    }


}
