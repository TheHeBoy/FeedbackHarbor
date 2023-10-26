package cn.iocoder.yudao.module.harbor.controller.app.comment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.comment.CommentDO;
import cn.iocoder.yudao.module.harbor.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "App - 评论")
@RestController
@RequestMapping("/harbor/comment")
@Validated
public class AppCommentController {

    @Resource
    private CommentService commentService;

    @PostMapping("/create")
    @PreAuthenticated
    @Operation(summary = "创建评论")
    public CommonResult<AppCommentRespVO> createComment(@Valid @RequestBody AppCommentCreateReqVO createReqVO) {
        return success(commentService.createComment(createReqVO, getLoginUserId()));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论分页")
    public CommonResult<PageResult<AppCommentPageRespVO>> getCommentPage(@Valid AppCommentPageReqVO pageVO) {
        return success(commentService.getCommentPage(pageVO));
    }

    @GetMapping("/replyPage")
    @Operation(summary = "获得回复分页")
    public CommonResult<PageResult<AppReplyVO>> getReplyPage(@Valid AppReplyPageReqVO pageVO) {
        return success(commentService.getReplyPage(pageVO));
    }
}
