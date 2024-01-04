package cn.hh.harbor.module.harbor.job;

import cn.hh.harbor.framework.quartz.core.handler.JobHandler;
import cn.hh.harbor.framework.tenant.core.util.TenantUtils;
import cn.hh.harbor.module.harbor.enums.common.BusTypeEnum;
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
            likeService.syncLike(BusTypeEnum.FEEDBACK);
            likeService.syncLike(BusTypeEnum.COMMENT);
        });
        return "同步点赞";
    }

}
