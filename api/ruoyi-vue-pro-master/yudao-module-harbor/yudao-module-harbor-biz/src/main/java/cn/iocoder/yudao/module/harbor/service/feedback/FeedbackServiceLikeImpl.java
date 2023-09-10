package cn.iocoder.yudao.module.harbor.service.feedback;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackLikeReqVO;
import cn.iocoder.yudao.module.harbor.convert.feedback.FeedbackLikeConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackLikeDO;
import cn.iocoder.yudao.module.harbor.dal.mysql.feedback.FeedbackLikeMapper;
import cn.iocoder.yudao.module.harbor.dal.redis.feedback.FeedbackLikeRedisDAO;
import cn.iocoder.yudao.module.harbor.dal.redis.feedback.FeedbackLikeRedisVO;
import cn.iocoder.yudao.module.harbor.enums.feedback.FeedbackLikeEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.harbor.enums.feedback.FeedbackLikeEnum.LIKED;
import static cn.iocoder.yudao.module.harbor.enums.feedback.FeedbackLikeEnum.LIKED_CANCEL;

/**
 * 用户反馈 Service 实现类
 * <p>
 */
@Service
@Validated
public class FeedbackServiceLikeImpl implements FeedbackLikeService {

    @Resource
    private FeedbackLikeRedisDAO feedbackLikeRedisDAO;

    @Resource
    private FeedbackLikeMapper feedbackLikeMapper;

    @Override
    public boolean like(AppFeedbackLikeReqVO likeReqVO, Long uid) {
        FeedbackLikeRedisVO redisDO = FeedbackLikeConvert.INSTANCE.convert(likeReqVO);
        redisDO.setUid(String.valueOf(uid));
        boolean isLiked = isLiked(redisDO);
        //用户已经点赞了
        if (isLiked) {
            Long count = feedbackLikeRedisDAO.remove(redisDO, LIKED);
            //删除了点赞的记录就不需要添加取消点赞的记录了
            if (count == 0) {
                feedbackLikeRedisDAO.set(redisDO, LIKED_CANCEL);
            }
        } else {
            feedbackLikeRedisDAO.set(redisDO, LIKED);
        }
        return !isLiked;
    }

    @Override
    public Set<Long> listByUid(Long uid) {
        return listByUid(uid, LIKED);
    }

    private boolean isLiked(FeedbackLikeRedisVO redisDO) {
        Boolean isLiked = feedbackLikeRedisDAO.isMember(redisDO, LIKED);

        if (isLiked) {
            return true;
        }

        Boolean isCancelLiked = feedbackLikeRedisDAO.isMember(redisDO, LIKED_CANCEL);

        //redis中没有点赞和取消点赞的数据从数据库查询
        if (!isCancelLiked) {
            FeedbackLikeDO feedbackLikeDO = feedbackLikeMapper.getByUidAndFeedbackId(redisDO);
            return ObjectUtil.isNotNull(feedbackLikeDO) && ObjectUtil.equal(feedbackLikeDO.getLiked(), LIKED.getCode());
        }

        return false;
    }


    /**
     * 通过用户id得到所有反馈id
     *
     * @param uid      用户id
     * @param likeEnum 点赞类型
     * @return {@link List}<{@link Integer}>
     */
    private Set<Long> listByUid(Long uid, FeedbackLikeEnum likeEnum) {
        Set<Long> feedbackIds = new HashSet<>();
        //得到所有点赞或取消点赞的key
        Set<String> keyList = feedbackLikeRedisDAO.list(likeEnum);

        for (String key : keyList) {
            String commentId = key.split(":")[2];
            Set<Long> uidSet = feedbackLikeRedisDAO.sGet(key);
            for (Long uid2 : uidSet) {
                if (ObjectUtil.equal(uid2, uid)) {
                    feedbackIds.add(Long.valueOf(commentId));
                }
            }
        }
        return feedbackIds;
    }
}