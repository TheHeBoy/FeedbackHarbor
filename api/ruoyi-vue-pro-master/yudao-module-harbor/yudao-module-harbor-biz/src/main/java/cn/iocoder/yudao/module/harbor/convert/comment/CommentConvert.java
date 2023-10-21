package cn.iocoder.yudao.module.harbor.convert.comment;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentPageRespVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.ReplyVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.harbor.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.comment.CommentDO;

/**
 * 评论 Convert
 *
 *  hehong
 */
@Mapper
public interface CommentConvert {

    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

    CommentDO convert(CommentCreateReqVO bean);

    CommentDO convert(CommentUpdateReqVO bean);

    CommentRespVO convert(CommentDO bean);

    List<CommentRespVO> convertList(List<CommentDO> list);

    PageResult<CommentRespVO> convertPage(PageResult<CommentDO> page);
    PageResult<ReplyVO> convertReplyPage(PageResult<CommentDO> page);

    PageResult<AppCommentPageRespVO> convertPageApp(PageResult<CommentDO> page);

    List<ReplyVO> convertListReply(List<CommentDO> list);

    List<CommentExcelVO> convertList02(List<CommentDO> list);

    CommentDO convert(AppCommentCreateReqVO createReqVO);
}
