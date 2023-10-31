package cn.iocoder.yudao.module.harbor.service.feedback;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentBaseVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackBaseVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.iocoder.yudao.module.harbor.convert.feedbacktag.FeedbackTagConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import cn.iocoder.yudao.module.harbor.dal.redis.like.LikeRedisDAO;
import cn.iocoder.yudao.module.harbor.enums.feedback.FeedbackReplyStateEnum;
import cn.iocoder.yudao.module.harbor.enums.like.LikeBusTypeEnum;
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
    public AppFeedbackRespVO createFeedback(AppFeedbackCreateReqVO createReqVO, Long uid) {
        // 插入
        FeedbackDO feedbackDO = FeedbackConvert.INSTANCE.convert(createReqVO);
        feedbackDO.setUid(uid);
        feedbackDO.setReplyState(FeedbackReplyStateEnum.NO_REPLY.getCode());
        feedbackMapper.insert(feedbackDO);

        // 填充用户信息
        feedbackDO = feedbackMapper.selectById(feedbackDO.getId());
        AppFeedbackRespVO feedbackRespVO = FeedbackConvert.INSTANCE.convertApp(feedbackDO);
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
        long count = likeRedisDAO.sSize(feedbackId, true, LikeBusTypeEnum.FEEDBACK);
        long cancelCount = likeRedisDAO.sSize(feedbackId, false, LikeBusTypeEnum.FEEDBACK);
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
        AppUserDO user = appUserService.getUser(vo.getUid());
        if (ObjectUtil.isNotNull(user)) {
            vo.setAvatar(user.getAvatar());
            vo.setNickname(user.getNickname());
            vo.setUserType(user.getUserType());
        }
    }

}
