package cn.hh.harbor.module.harbor.service.feedback;

import cn.hh.harbor.module.harbor.controller.app.feedback.vo.*;
import cn.hh.harbor.module.system.api.sensitiveword.SensitiveWordApi;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hh.harbor.module.harbor.convert.feedbacktag.FeedbackTagConvert;
import cn.hh.harbor.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import cn.hh.harbor.module.harbor.dal.redis.like.LikeRedisDAO;
import cn.hh.harbor.module.harbor.enums.feedback.FeedbackReplyStateEnum;
import cn.hh.harbor.module.harbor.enums.common.BusTypeEnum;
import cn.hh.harbor.module.harbor.service.comment.CommentService;
import cn.hh.harbor.module.harbor.service.feedbacktag.FeedbackTagService;
import cn.hh.harbor.module.system.api.user.UserApi;
import cn.hh.harbor.module.system.api.user.dto.UserRespDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.hh.harbor.module.harbor.controller.admin.feedback.vo.*;
import cn.hh.harbor.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.hh.harbor.framework.common.pojo.PageResult;

import cn.hh.harbor.module.harbor.convert.feedback.FeedbackConvert;
import cn.hh.harbor.module.harbor.dal.mysql.feedback.FeedbackMapper;

import static cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.hh.harbor.module.harbor.enums.ErrorCodeConstants.*;

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
    private UserApi userApi;

    @Resource
    private LikeRedisDAO likeRedisDAO;

    @Resource
    private CommentService commentService;

    @Resource
    private FeedbackTagService feedbackTagService;

    @Resource
    private FeedbackTagConvert feedbackTagConvert;

    @Resource
    private SensitiveWordApi sensitiveWordApi;

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
    public PageResult<FeedbackRespVO> getFeedbackPage(FeedbackPageReqVO pageReqVO) {
        PageResult<FeedbackDO> entitiesPage = feedbackMapper.selectPage(pageReqVO);
        ArrayList<FeedbackRespVO> respVOS = new ArrayList<>();
        for (FeedbackDO feedbackDO : entitiesPage.getList()) {
            FeedbackRespVO vo = FeedbackConvert.INSTANCE.convert(feedbackDO);
            fill(vo, feedbackDO, false);
            respVOS.add(vo);
        }
        return new PageResult<>(respVOS, entitiesPage.getTotal());
    }

    @Override
    public AppFeedbackCreateRespVO createFeedback(AppFeedbackCreateReqVO createReqVO, Long uid) {
        List<String> sensitiveList = sensitiveWordApi.validateText(createReqVO.getContent(), null);
        if (CollUtil.isNotEmpty(sensitiveList)) {
            return new AppFeedbackCreateRespVO().setSensitive(sensitiveList);
        }

        // 插入
        FeedbackDO feedbackDO = FeedbackConvert.INSTANCE.convert(createReqVO);
        feedbackDO.setUid(uid);
        feedbackDO.setReplyState(FeedbackReplyStateEnum.NO_REPLY.getCode());
        feedbackMapper.insert(feedbackDO);

        // 填充用户信息
        feedbackDO = feedbackMapper.selectById(feedbackDO.getId());
        AppFeedbackCreateRespVO feedbackRespVO = FeedbackConvert.INSTANCE.convertApp1(feedbackDO);
        fill(feedbackRespVO, feedbackDO, true);
        return feedbackRespVO;
    }

    @Override
    public PageResult<AppFeedbackRespVO> getFeedbackPage(AppFeedbackPageReqVO pageVO) {
        PageResult<FeedbackDO> entitiesPage = feedbackMapper.selectPage(pageVO);
        ArrayList<AppFeedbackRespVO> respVOS = new ArrayList<>();
        for (FeedbackDO feedbackDO : entitiesPage.getList()) {
            AppFeedbackRespVO vo = FeedbackConvert.INSTANCE.convertApp(feedbackDO);
            fill(vo, feedbackDO, false);
            respVOS.add(vo);
        }
        return new PageResult<>(respVOS, entitiesPage.getTotal());
    }

    /**
     * 获取redis中的反馈点赞数量
     *
     * @param feedbackId 反馈id
     * @return void
     */
    private Long getLikeCount(Long feedbackId) {
        long count = likeRedisDAO.sSize(feedbackId, true, BusTypeEnum.FEEDBACK);
        long cancelCount = likeRedisDAO.sSize(feedbackId, false, BusTypeEnum.FEEDBACK);
        return count - cancelCount;
    }

    /**
     * 填充点赞数、评论数量、用户信息和反馈标签
     *
     * @param isCreate 是否填充新建数据
     */
    private void fill(AppFeedbackBaseVO vo, FeedbackDO feedbackDO, boolean isCreate) {
        if (!isCreate) {
            // 合并缓存中的点赞数
            vo.setLikes(vo.getLikes() + getLikeCount(vo.getId()));

            // 评论数量
            vo.setCommentNum(commentService.getCommentNum(vo.getId()));
        }

        // 反馈标签
        FeedbackTagDO feedbackTag = feedbackTagService.getFeedbackTag(feedbackDO.getFeedbackTagId());
        vo.setFeedbackTag(feedbackTagConvert.convertApp(feedbackTag));

        // 用户信息
        UserRespDTO user = userApi.getUser(vo.getUid());
        if (ObjectUtil.isNotNull(user)) {
            vo.setAvatar(user.getAvatar());
            vo.setNickname(user.getNickname());
            vo.setUserType(user.getUserType());
        }
    }

}
