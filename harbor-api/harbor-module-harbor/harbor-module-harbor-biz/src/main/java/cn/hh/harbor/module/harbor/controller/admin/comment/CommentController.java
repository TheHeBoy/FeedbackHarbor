package cn.hh.harbor.module.harbor.controller.admin.comment;

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

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.common.pojo.CommonResult;

import static cn.hh.harbor.framework.common.pojo.CommonResult.success;

import cn.hh.harbor.framework.excel.core.util.ExcelUtils;

import cn.hh.harbor.framework.operatelog.core.annotations.OperateLog;

import static cn.hh.harbor.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.hh.harbor.module.harbor.controller.admin.comment.vo.*;
import cn.hh.harbor.module.harbor.dal.dataobject.comment.CommentDO;
import cn.hh.harbor.module.harbor.convert.comment.CommentConvert;
import cn.hh.harbor.module.harbor.service.comment.CommentService;

@Tag(name = "管理后台 - 评论")
@RestController
@RequestMapping("/harbor/comment")
@Validated
public class CommentController {

    @Resource
    private CommentService commentService;

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('harbor:comment:delete')")
    public CommonResult<Boolean> deleteComment(@RequestParam("id") Long id) {
        commentService.deleteComment(id);
        return success(true);
    }
}
