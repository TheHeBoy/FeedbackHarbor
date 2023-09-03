package cn.iocoder.yudao.module.uservoice.service.comment;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.uservoice.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.uservoice.controller.app.comment.vo.AppCommentCreateReqVO;
import cn.iocoder.yudao.module.uservoice.controller.app.comment.vo.AppCommentPageReqVO;
import cn.iocoder.yudao.module.uservoice.controller.app.comment.vo.AppCommentPageRespVO;
import cn.iocoder.yudao.module.uservoice.dal.dataobject.comment.CommentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 评论 Service 接口
 *
 *  hehong
 */
public interface CommentService {

    /**
     * 创建评论
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createComment(@Valid CommentCreateReqVO createReqVO);

    CommentDO createComment(@Valid AppCommentCreateReqVO createReqVO);

    /**
     * 更新评论
     *
     * @param updateReqVO 更新信息
     */
    void updateComment(@Valid CommentUpdateReqVO updateReqVO);

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
}
