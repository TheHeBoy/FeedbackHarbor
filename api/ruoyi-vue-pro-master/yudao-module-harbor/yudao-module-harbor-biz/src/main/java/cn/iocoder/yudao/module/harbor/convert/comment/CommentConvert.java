package cn.iocoder.yudao.module.harbor.convert.comment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.harbor.controller.admin.comment.vo.CommentCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.admin.comment.vo.CommentRespVO;
import cn.iocoder.yudao.module.harbor.controller.admin.comment.vo.CommentUpdateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentPageRespVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentRespVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppReplyVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.comment.CommentDO;
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
