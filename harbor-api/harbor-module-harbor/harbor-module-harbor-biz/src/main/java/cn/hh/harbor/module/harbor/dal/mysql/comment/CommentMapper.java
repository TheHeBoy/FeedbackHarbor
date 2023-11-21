package cn.hh.harbor.module.harbor.dal.mysql.comment;

import java.util.*;

import cn.hh.harbor.framework.common.pojo.PageParam;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.module.harbor.controller.app.comment.vo.AppCommentPageReqVO;
import cn.hh.harbor.module.harbor.controller.app.comment.vo.AppReplyPageReqVO;
import cn.hh.harbor.module.harbor.dal.dataobject.comment.CommentDO;
import org.apache.ibatis.annotations.Mapper;
import cn.hh.harbor.module.harbor.controller.admin.comment.vo.*;

/**
 * 评论 Mapper
 * <p>
 * hehong
 */
@Mapper
public interface CommentMapper extends BaseMapperX<CommentDO> {


    default PageResult<CommentDO> selectPage(AppCommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CommentDO>()
                .eqIfPresent(CommentDO::getFeedbackId, reqVO.getFeedbackId())
                .isNull(CommentDO::getParentId)
                .orderByDesc(CommentDO::getId));
    }

    default PageResult<CommentDO> selectReplyPage(PageParam pageVO, Long commentId) {
        return selectPage(pageVO, new LambdaQueryWrapperX<CommentDO>()
                .eq(CommentDO::getParentId, commentId));
    }
}
