package cn.hh.harbor.module.harbor.service.comment;

import javax.validation.*;

import cn.hh.harbor.module.harbor.controller.admin.comment.vo.*;
import cn.hh.harbor.module.harbor.controller.app.comment.vo.*;
import cn.hh.harbor.module.harbor.dal.dataobject.comment.CommentDO;
import cn.hh.harbor.framework.common.pojo.PageResult;

/**
 * 评论 Service 接口
 * <p>
 * hehong
 */
public interface CommentService {

    AppCommentCreateRespVO createComment(@Valid AppCommentCreateReqVO createReqVO, Long uid);


    /**
     * 删除评论
     *
     * @param id 编号
     */
    void deleteComment(Long id);


    PageResult<AppCommentPageRespVO> getCommentPage(AppCommentPageReqVO pageVO);

    /**
     * 得到评论数量
     * @param feedbackId 反馈id
     * @return int
     */
    Long getCommentNum(Long feedbackId);

    /**
     * 回复分页
     * @param pageVO 分页参数
     * @return 分页结果
     */
    PageResult<AppReplyVO> getReplyPage(AppReplyPageReqVO pageVO);
}
