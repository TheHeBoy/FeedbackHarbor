package cn.iocoder.yudao.module.harbor.controller.admin.feedback;

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

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.harbor.convert.feedback.FeedbackConvert;
import cn.iocoder.yudao.module.harbor.service.feedback.FeedbackService;

@Tag(name = "管理后台 - 用户反馈")
@RestController
@RequestMapping("/harbor/feedback")
@Validated
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @PostMapping("/create")
    @Operation(summary = "创建用户反馈")
    @PreAuthorize("@ss.hasPermission('uservoice:feedback:create')")
    public CommonResult<Long> createFeedback(@Valid @RequestBody FeedbackCreateReqVO createReqVO) {
        return success(feedbackService.createFeedback(createReqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户反馈")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('uservoice:feedback:delete')")
    public CommonResult<Boolean> deleteFeedback(@RequestParam("id") Long id) {
        feedbackService.deleteFeedback(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户反馈")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('uservoice:feedback:query')")
    public CommonResult<FeedbackRespVO> getFeedback(@RequestParam("id") Long id) {
        FeedbackDO feedback = feedbackService.getFeedback(id);
        return success(FeedbackConvert.INSTANCE.convert(feedback));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户反馈分页")
    @PreAuthorize("@ss.hasPermission('uservoice:feedback:query')")
    public CommonResult<PageResult<FeedbackRespVO>> getFeedbackPage(@Valid FeedbackPageReqVO pageVO) {
        PageResult<FeedbackDO> pageResult = feedbackService.getFeedbackPage(pageVO);
        return success(FeedbackConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户反馈 Excel")
    @PreAuthorize("@ss.hasPermission('uservoice:feedback:export')")
    @OperateLog(type = EXPORT)
    public void exportFeedbackExcel(@Valid FeedbackExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FeedbackDO> list = feedbackService.getFeedbackList(exportReqVO);
        // 导出 Excel
        List<FeedbackExcelVO> datas = FeedbackConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "用户反馈.xls", "数据", FeedbackExcelVO.class, datas);
    }

}
