package cn.iocoder.yudao.module.uservoice.controller.app.comment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.uservoice.controller.admin.comment.vo.CommentPageReqVO;
import cn.iocoder.yudao.module.uservoice.controller.admin.comment.vo.CommentRespVO;
import cn.iocoder.yudao.module.uservoice.controller.app.comment.vo.*;
import cn.iocoder.yudao.module.uservoice.convert.comment.CommentConvert;
import cn.iocoder.yudao.module.uservoice.dal.dataobject.comment.CommentDO;
import cn.iocoder.yudao.module.uservoice.service.comment.CommentService;
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
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "App - 评论")
@RestController
@RequestMapping("/uservoice/comment")
@Validated
public class AppCommentController {

    @Resource
    private CommentService commentService;

    @PostMapping("/create")
    @PreAuthenticated
    @Operation(summary = "创建评论")
    public CommonResult<CommentDO> createComment(@Valid @RequestBody AppCommentCreateReqVO createReqVO) {
        return success(commentService.createComment(createReqVO));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论分页")
    @PermitAll
    public CommonResult<PageResult<AppCommentPageRespVO>> getCommentPage(@Valid AppCommentPageReqVO pageVO) {
        return success(commentService.getCommentPage(pageVO));
    }
}
