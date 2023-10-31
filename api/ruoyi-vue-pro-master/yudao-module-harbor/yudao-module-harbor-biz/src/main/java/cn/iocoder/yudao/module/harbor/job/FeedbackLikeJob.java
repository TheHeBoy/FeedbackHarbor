package cn.iocoder.yudao.module.harbor.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.harbor.dal.mysql.comment.CommentMapper;
import cn.iocoder.yudao.module.harbor.dal.mysql.feedback.FeedbackMapper;
import cn.iocoder.yudao.module.harbor.enums.like.LikeBusTypeEnum;
import cn.iocoder.yudao.module.harbor.service.like.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 同步反馈点赞到数据库
 *
 * @author hehong
 * @date 2023-09-15
 */
@Slf4j
@Component("FeedbackLikeJob")
@TenantJob
public class FeedbackLikeJob implements JobHandler {

    @Resource
    private LikeService likeService;

    @Override
    public String execute(String param) {
        return "同步点赞数量：" + (likeService.syncLike(LikeBusTypeEnum.FEEDBACK)
                + likeService.syncLike(LikeBusTypeEnum.COMMENT));
    }

}
