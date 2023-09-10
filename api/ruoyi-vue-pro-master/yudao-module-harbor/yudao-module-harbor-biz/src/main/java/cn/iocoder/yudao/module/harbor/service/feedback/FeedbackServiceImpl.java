package cn.iocoder.yudao.module.harbor.service.feedback;

import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.dal.redis.feedback.FeedbackLikeRedisDAO;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.harbor.convert.feedback.FeedbackConvert;
import cn.iocoder.yudao.module.harbor.dal.mysql.feedback.FeedbackMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.harbor.enums.feedback.FeedbackLikeEnum.LIKED;
import static cn.iocoder.yudao.module.harbor.enums.feedback.FeedbackLikeEnum.LIKED_CANCEL;

/**
 * 用户反馈 Service 实现类
 * <p>
 */
@Service
@Validated
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    @Resource
    private AppUserService appUserService;

    @Resource
    private FeedbackLikeRedisDAO feedbackLikeRedisDAO;

    @Override
    public Long createFeedback(FeedbackCreateReqVO createReqVO) {
        // 插入
        FeedbackDO feedback = FeedbackConvert.INSTANCE.convert(createReqVO);
        feedbackMapper.insert(feedback);
        // 返回
        return feedback.getId();
    }

    @Override
    public void updateFeedback(FeedbackUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedbackExists(updateReqVO.getId());
        // 更新
        FeedbackDO updateObj = FeedbackConvert.INSTANCE.convert(updateReqVO);
        feedbackMapper.updateById(updateObj);
    }

    @Override
    public void deleteFeedback(Long id) {
        // 校验存在
        validateFeedbackExists(id);
        // 删除
        feedbackMapper.deleteById(id);
    }

    private void validateFeedbackExists(Long id) {
        if (feedbackMapper.selectById(id) == null) {
            throw exception(FEEDBACK_NOT_EXISTS);
        }
    }

    @Override
    public FeedbackDO getFeedback(Long id) {
        FeedbackDO feedbackDO = feedbackMapper.selectById(id);
        feedbackDO.setLikes(feedbackDO.getLikes() + getLikeCount(feedbackDO.getId()));
        return feedbackDO;
    }

    @Override
    public PageResult<FeedbackDO> getFeedbackPage(FeedbackPageReqVO pageReqVO) {
        return feedbackMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FeedbackDO> getFeedbackList(FeedbackExportReqVO exportReqVO) {
        return feedbackMapper.selectList(exportReqVO);
    }

    @Override
    public FeedbackDO createFeedback(AppFeedbackCreateReqVO createReqVO,Long uid) {
        // 插入
        FeedbackDO feedback = FeedbackConvert.INSTANCE.convert(createReqVO);
        AppUserDO user = appUserService.getUser(uid);
        feedback.setUid(user.getId());
        feedback.setAvatar(user.getAvatar());
        feedback.setNickname(user.getNickname());
        feedback.setUserType(user.getUserType());
        feedbackMapper.insert(feedback);
        return feedbackMapper.selectById(feedback.getId());
    }

    @Override
    public PageResult<FeedbackDO> getFeedbackPage(AppFeedbackPageReqVO pageVO) {
        PageResult<FeedbackDO> feedbackDOPageResult = feedbackMapper.selectPage(pageVO);
        feedbackDOPageResult.getList().forEach(e -> {
            Long likeCount = getLikeCount(e.getId());
            e.setLikes(e.getLikes() + likeCount);
        });
        return feedbackDOPageResult;
    }

    /**
     * 获取redis中的反馈点赞数量
     *
     * @param feedbackId 反馈id
     * @return void
     */
    private Long getLikeCount(Long feedbackId) {
        long count = feedbackLikeRedisDAO.sSize(String.valueOf(feedbackId), LIKED);
        long cancelCount = feedbackLikeRedisDAO.sSize(String.valueOf(feedbackId), LIKED_CANCEL);
        return count - cancelCount;
    }
}