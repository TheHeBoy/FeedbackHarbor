package cn.hh.harbor.module.harbor.convert.comment;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.harbor.controller.admin.comment.vo.CommentCreateReqVO;
import cn.hh.harbor.module.harbor.controller.admin.comment.vo.CommentRespVO;
import cn.hh.harbor.module.harbor.controller.admin.comment.vo.CommentUpdateReqVO;
import cn.hh.harbor.module.harbor.controller.app.comment.vo.AppCommentCreateReqVO;
import cn.hh.harbor.module.harbor.controller.app.comment.vo.AppCommentPageRespVO;
import cn.hh.harbor.module.harbor.controller.app.comment.vo.AppCommentRespVO;
import cn.hh.harbor.module.harbor.controller.app.comment.vo.AppReplyVO;
import cn.hh.harbor.module.harbor.dal.dataobject.comment.CommentDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 评论 Convert
 * <p>
 * hehong
 */
@Mapper
public interface CommentConvert {

    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

    CommentDO convert(CommentCreateReqVO bean);

    CommentDO convert(CommentUpdateReqVO bean);

    CommentRespVO convert(CommentDO bean);

    PageResult<AppReplyVO> convertReplyPage(PageResult<CommentDO> page);

    PageResult<AppCommentPageRespVO> convertPageApp(PageResult<CommentDO> page);

    CommentDO convert(AppCommentCreateReqVO createReqVO);

    AppCommentRespVO convertApp(CommentDO commentDO);
}
