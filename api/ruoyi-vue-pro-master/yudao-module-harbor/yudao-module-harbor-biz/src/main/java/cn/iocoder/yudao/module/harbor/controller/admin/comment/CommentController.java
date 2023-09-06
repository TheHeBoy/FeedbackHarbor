package cn.iocoder.yudao.module.harbor.controller.admin.comment;

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

import cn.iocoder.yudao.module.harbor.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.comment.CommentDO;
import cn.iocoder.yudao.module.harbor.convert.comment.CommentConvert;
import cn.iocoder.yudao.module.harbor.service.comment.CommentService;

@Tag(name = "管理后台 - 评论")
@RestController
@RequestMapping("/harbor/comment")
@Validated
public class CommentController {

    @Resource
    private CommentService commentService;

    @PostMapping("/create")
    @Operation(summary = "创建评论")
    @PreAuthorize("@ss.hasPermission('uservoice:comment:create')")
    public CommonResult<Long> createComment(@Valid @RequestBody CommentCreateReqVO createReqVO) {
        return success(commentService.createComment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评论")
    @PreAuthorize("@ss.hasPermission('uservoice:comment:update')")
    public CommonResult<Boolean> updateComment(@Valid @RequestBody CommentUpdateReqVO updateReqVO) {
        commentService.updateComment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('uservoice:comment:delete')")
    public CommonResult<Boolean> deleteComment(@RequestParam("id") Long id) {
        commentService.deleteComment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('uservoice:comment:query')")
    public CommonResult<CommentRespVO> getComment(@RequestParam("id") Long id) {
        CommentDO comment = commentService.getComment(id);
        return success(CommentConvert.INSTANCE.convert(comment));
    }

    @GetMapping("/list")
    @Operation(summary = "获得评论列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('uservoice:comment:query')")
    public CommonResult<List<CommentRespVO>> getCommentList(@RequestParam("ids") Collection<Long> ids) {
        List<CommentDO> list = commentService.getCommentList(ids);
        return success(CommentConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论分页")
    @PreAuthorize("@ss.hasPermission('uservoice:comment:query')")
    public CommonResult<PageResult<CommentRespVO>> getCommentPage(@Valid CommentPageReqVO pageVO) {
        PageResult<CommentDO> pageResult = commentService.getCommentPage(pageVO);
        return success(CommentConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评论 Excel")
    @PreAuthorize("@ss.hasPermission('uservoice:comment:export')")
    @OperateLog(type = EXPORT)
    public void exportCommentExcel(@Valid CommentExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<CommentDO> list = commentService.getCommentList(exportReqVO);
        // 导出 Excel
        List<CommentExcelVO> datas = CommentConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "评论.xls", "数据", CommentExcelVO.class, datas);
    }

}
