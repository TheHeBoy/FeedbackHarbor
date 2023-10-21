package cn.iocoder.yudao.module.harbor.service.feedback;

import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.iocoder.yudao.module.harbor.convert.feedbacktag.FeedbackTagConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import cn.iocoder.yudao.module.harbor.dal.redis.like.LikeRedisDAO;
import cn.iocoder.yudao.module.harbor.enums.like.LikeBusTypeEnum;
import cn.iocoder.yudao.module.harbor.job.FeedbackLikeJob;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserService;
import cn.iocoder.yudao.module.harbor.service.comment.CommentService;
import cn.iocoder.yudao.module.harbor.service.feedbacktag.FeedbackTagService;
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
import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.*;

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
    private LikeRedisDAO likeRedisDAO;

    @Resource
    private CommentService commentService;

    @Resource
    private FeedbackTagService feedbackTagService;

    @Resource
    private FeedbackTagConvert feedbackTagConvert;

    @Override
    public Long createFeedback(FeedbackCreateReqVO createReqVO) {
        // 插入
        FeedbackDO feedback = FeedbackConvert.INSTANCE.convert(createReqVO);
        feedbackMapper.insert(feedback);
        // 返回
        return feedback.getId();
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
    public FeedbackDO createFeedback(AppFeedbackCreateReqVO createReqVO, Long uid) {
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
    public PageResult<AppFeedbackRespVO> getFeedbackPage(AppFeedbackPageReqVO pageVO) {
        PageResult<FeedbackDO> entitiesPage = feedbackMapper.selectPage(pageVO);
        ArrayList<AppFeedbackRespVO> respVOS = new ArrayList<>();
        for (FeedbackDO feedbackDO : entitiesPage.getList()) {
            AppFeedbackRespVO e = FeedbackConvert.INSTANCE.convertPageApp(feedbackDO);

            // 合并缓存中的点赞数
            e.setLikes(e.getLikes() + getLikeCount(e.getId()));

            // 评论数量
            e.setCommentNum(commentService.getCommentNum(e.getId()));

            // 反馈标签
            FeedbackTagDO feedbackTag = feedbackTagService.getFeedbackTag(feedbackDO.getFeedbackTagId());
            e.setFeedbackTag(feedbackTagConvert.convertApp(feedbackTag));

            respVOS.add(e);
        }
        PageResult<AppFeedbackRespVO> pageResult = new PageResult<>();
        pageResult.setTotal(entitiesPage.getTotal());
        pageResult.setList(respVOS);
        return pageResult;
    }

    /**
     * 获取redis中的反馈点赞数量
     *
     * @param feedbackId 反馈id
     * @return void
     */
    private Long getLikeCount(Long feedbackId) {
        long count = likeRedisDAO.sSize(feedbackId, true, LikeBusTypeEnum.FEEDBACK);
        long cancelCount = likeRedisDAO.sSize(feedbackId, false, LikeBusTypeEnum.FEEDBACK);
        return count - cancelCount;
    }
}
