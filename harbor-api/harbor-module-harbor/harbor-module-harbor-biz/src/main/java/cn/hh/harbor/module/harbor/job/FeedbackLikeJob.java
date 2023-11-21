package cn.hh.harbor.module.harbor.job;

import cn.hh.harbor.framework.quartz.core.handler.JobHandler;
import cn.hh.harbor.framework.tenant.core.aop.TenantIgnore;
import cn.hh.harbor.framework.tenant.core.context.TenantContextHolder;
import cn.hh.harbor.framework.tenant.core.job.TenantJob;
import cn.hh.harbor.framework.tenant.core.util.TenantUtils;
import cn.hh.harbor.module.harbor.dal.mysql.comment.CommentMapper;
import cn.hh.harbor.module.harbor.dal.mysql.feedback.FeedbackMapper;
import cn.hh.harbor.module.harbor.enums.like.LikeBusTypeEnum;
import cn.hh.harbor.module.harbor.service.like.LikeService;
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
public class FeedbackLikeJob implements JobHandler {

    @Resource
    private LikeService likeService;

    @Override
    public String execute(String param) {
        TenantUtils.executeIgnore(() -> {
            likeService.syncLike(LikeBusTypeEnum.FEEDBACK);
            likeService.syncLike(LikeBusTypeEnum.COMMENT);
        });
        return "同步点赞";
    }

}
