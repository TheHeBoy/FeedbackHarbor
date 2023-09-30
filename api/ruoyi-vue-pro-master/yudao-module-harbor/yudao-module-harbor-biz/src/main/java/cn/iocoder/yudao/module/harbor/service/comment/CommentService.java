package cn.iocoder.yudao.module.harbor.service.comment;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.harbor.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentPageRespVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.comment.CommentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 评论 Service 接口
 * <p>
 * hehong
 */
public interface CommentService {

    CommentDO createComment(@Valid AppCommentCreateReqVO createReqVO, Long uid);


    /**
     * 删除评论
     *
     * @param id 编号
     */
    void deleteComment(Long id);

    /**
     * 获得评论
     *
     * @param id 编号
     * @return 评论
     */
    CommentDO getComment(Long id);

    /**
     * 获得评论列表
     *
     * @param ids 编号
     * @return 评论列表
     */
    List<CommentDO> getCommentList(Collection<Long> ids);

    /**
     * 获得评论分页
     *
     * @param pageReqVO 分页查询
     * @return 评论分页
     */
    PageResult<CommentDO> getCommentPage(CommentPageReqVO pageReqVO);

    /**
     * 获得评论列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 评论列表
     */
    List<CommentDO> getCommentList(CommentExportReqVO exportReqVO);

    PageResult<AppCommentPageRespVO> getCommentPage(AppCommentPageReqVO pageVO);

    /**
     * 得到评论数量
     * @param feedbackId 反馈id
     * @return int
     */
    Long getCommentNum(Long feedbackId);
}
