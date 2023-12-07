package cn.hh.harbor.module.harbor.service.comment;

import cn.hh.harbor.module.system.api.sensitiveword.SensitiveWordApi;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.module.harbor.controller.app.comment.vo.*;
import cn.hh.harbor.module.harbor.dal.mysql.feedback.FeedbackMapper;
import cn.hh.harbor.module.harbor.dal.redis.like.LikeRedisDAO;
import cn.hh.harbor.module.harbor.enums.feedback.FeedbackReplyStateEnum;
import cn.hh.harbor.module.harbor.enums.common.BusTypeEnum;
import cn.hh.harbor.module.system.api.user.UserApi;
import cn.hh.harbor.module.system.api.user.dto.UserRespDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import cn.hh.harbor.module.harbor.dal.dataobject.comment.CommentDO;
import cn.hh.harbor.framework.common.pojo.PageResult;

import cn.hh.harbor.module.harbor.convert.comment.CommentConvert;
import cn.hh.harbor.module.harbor.dal.mysql.comment.CommentMapper;

import java.util.List;

import static cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.hh.harbor.module.harbor.enums.ErrorCodeConstants.*;

/**
 * 评论 Service 实现类
 * <p>
 * hehong
 */
@Service
@Validated
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserApi userApi;

    @Resource
    private LikeRedisDAO likeRedisDAO;

    @Resource
    private FeedbackMapper feedbackMapper;

    @Resource
    private SensitiveWordApi sensitiveWordApi;

    @Override
    public void deleteComment(Long id) {
        // 校验存在
        validateCommentExists(id);
        // 删除
        commentMapper.deleteById(id);
    }

    private void validateCommentExists(Long id) {
        if (commentMapper.selectById(id) == null) {
            throw exception(COMMENT_NOT_EXISTS);
        }
    }

    @Override
    public PageResult<AppCommentPageRespVO> getCommentPage(AppCommentPageReqVO pageReqVO) {
        PageResult<AppCommentPageRespVO> commentPage = CommentConvert.INSTANCE.convertPageApp(commentMapper.selectPage(pageReqVO));

        // 一级评论
        for (AppCommentPageRespVO commentParent : commentPage.getList()) {

            fill(commentParent);

            // 添加回复
            AppReplyPageReqVO appReplyPageReqVO = new AppReplyPageReqVO();
            appReplyPageReqVO.setCommentId(commentParent.getId());
            appReplyPageReqVO.setPageSize(pageReqVO.getPageSize());
            appReplyPageReqVO.setPageNo(0);
            commentParent.setReplyPage(getSelf().getReplyPage(appReplyPageReqVO));
        }
        return commentPage;
    }

    @Override
    public Long getCommentNum(Long feedbackId) {
        return commentMapper.selectCount(CommentDO::getFeedbackId, feedbackId);
    }

    @Override
    public PageResult<AppReplyVO> getReplyPage(AppReplyPageReqVO pageVO) {
        PageResult<CommentDO> commentDOPageResult = commentMapper.selectReplyPage(pageVO, pageVO.getCommentId());

        // 填充
        PageResult<AppReplyVO> pageResult = CommentConvert.INSTANCE.convertReplyPage(commentDOPageResult);
        pageResult.getList().forEach(this::fill);

        return pageResult;
    }


    @Override
    public AppCommentCreateRespVO createComment(AppCommentCreateReqVO createReqVO, Long uid) {
        List<String> sensitiveList = sensitiveWordApi.validateText(createReqVO.getContent(), null);
        if (CollUtil.isNotEmpty(sensitiveList)) {
            return new AppCommentCreateRespVO().setSensitive(sensitiveList);
        }

        CommentDO commentDO = CommentConvert.INSTANCE.convert(createReqVO);
        commentDO.setUid(uid);
        commentMapper.insert(commentDO);

        // 填充用户信息
        commentDO = commentMapper.selectById(commentDO.getId());
        AppCommentCreateRespVO respVO = CommentConvert.INSTANCE.convertApp1(commentDO);
        fillUserInfo(respVO);

        // 如果是管理员用户，修改反馈回复状态为已回复
        if (ObjectUtil.equal(respVO.getUserType(), UserTypeEnum.ADMIN.getValue())) {
            feedbackMapper.updateReplyState(createReqVO.getFeedbackId(), FeedbackReplyStateEnum.REPLIED);
        }
        return CommentConvert.INSTANCE.convertApp1(commentDO);
    }

    /**
     * 获取redis中的反馈点赞数量
     *
     * @param rid 评论id
     * @return void
     */
    private Long getLikeCount(Long rid) {
        long count = likeRedisDAO.sSize(rid, true, BusTypeEnum.COMMENT);
        long cancelCount = likeRedisDAO.sSize(rid, false, BusTypeEnum.COMMENT);
        return count - cancelCount;
    }

    /**
     * 填充点赞数和用户信息
     */
    private void fill(AppCommentBaseVO vo) {
        // 合并缓存中的点赞数
        vo.setLikes(vo.getLikes() + getLikeCount(vo.getId()));

        // 用户信息
        fillUserInfo(vo);
    }

    /**
     * 填充用户信息
     */
    private void fillUserInfo(AppCommentBaseVO vo) {
        UserRespDTO user = userApi.getUser(vo.getUid());
        if (ObjectUtil.isNotNull(user)) {
            vo.setAvatar(user.getAvatar());
            vo.setNickname(user.getNickname());
            vo.setUserType(user.getUserType());
        }
    }

    /**
     * 获得自身的代理对象，解决 AOP 生效问题
     *
     * @return 自己
     */
    private CommentServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }
}
