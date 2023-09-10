package cn.iocoder.yudao.module.harbor.service.comment;

import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentPageRespVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.ReplyVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.harbor.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.comment.CommentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.harbor.convert.comment.CommentConvert;
import cn.iocoder.yudao.module.harbor.dal.mysql.comment.CommentMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.*;

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
    private AppUserService appUserService;

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
    public CommentDO getComment(Long id) {
        return commentMapper.selectById(id);
    }

    @Override
    public List<CommentDO> getCommentList(Collection<Long> ids) {
        return commentMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CommentDO> getCommentPage(CommentPageReqVO pageReqVO) {
        return commentMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<AppCommentPageRespVO> getCommentPage(AppCommentPageReqVO pageReqVO) {
        PageResult<AppCommentPageRespVO> commentPage = CommentConvert.INSTANCE.convertPageApp(commentMapper.selectPage(pageReqVO));

        //一级评论
        for (AppCommentPageRespVO commentParent : commentPage.getList()) {
            List<CommentDO> commentDOS = commentMapper.selectByPid(commentParent.getId());
            List<ReplyVO> replies = commentDOS.stream().map(CommentConvert.INSTANCE::convertReply).collect(Collectors.toList());
            commentParent.setReplies(replies);
        }
        return commentPage;
    }

    @Override
    public List<CommentDO> getCommentList(CommentExportReqVO exportReqVO) {
        return commentMapper.selectList(exportReqVO);
    }

    @Override
    public CommentDO createComment(AppCommentCreateReqVO createReqVO,Long uid) {
        CommentDO comment = CommentConvert.INSTANCE.convert(createReqVO);
        AppUserDO user = appUserService.getUser(uid);
        comment.setUid(user.getId());
        comment.setAvatar(user.getAvatar());
        comment.setNickname(user.getNickname());
        comment.setUserType(user.getUserType());
        commentMapper.insert(comment);
        return comment;
    }

}
