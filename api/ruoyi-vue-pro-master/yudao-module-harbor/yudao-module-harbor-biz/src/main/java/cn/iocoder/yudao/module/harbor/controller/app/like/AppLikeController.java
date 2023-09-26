package cn.iocoder.yudao.module.harbor.controller.app.like;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.harbor.controller.app.like.vo.AppLikeListReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.like.vo.AppLikeReqVO;
import cn.iocoder.yudao.module.harbor.enums.like.LikeBusTypeEnum;
import cn.iocoder.yudao.module.harbor.service.like.LikeService;
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
@RequestMapping("/harbor/like")
@Validated
public class AppLikeController {

    @Resource
    private LikeService likeService;

    @PostMapping("/like")
    @PreAuthenticated
    @Operation(summary = "点赞")
    public CommonResult<Boolean> like(@Valid @RequestBody AppLikeReqVO likeReqVO) {
        return success(likeService.like(getLoginUserId(), likeReqVO));
    }

    @GetMapping("/list")
    @Operation(summary = "点赞集合")
    public CommonResult<Set<Long>> listByUid(@Valid AppLikeListReqVO likeListReqVO) {
        return success(likeService.listByUid(getLoginUserId(), LikeBusTypeEnum.valueOf(likeListReqVO.getBusType())));
    }
}
