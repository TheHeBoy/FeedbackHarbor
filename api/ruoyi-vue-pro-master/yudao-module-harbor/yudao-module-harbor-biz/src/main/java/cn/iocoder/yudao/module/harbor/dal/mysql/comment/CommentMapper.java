package cn.iocoder.yudao.module.harbor.dal.mysql.comment;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppCommentPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.comment.vo.AppReplyPageReqVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.comment.CommentDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.harbor.controller.admin.comment.vo.*;

/**
 * 评论 Mapper
 * <p>
 * hehong
 */
@Mapper
public interface CommentMapper extends BaseMapperX<CommentDO> {

    default PageResult<CommentDO> selectPage(CommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CommentDO>()
                .betweenIfPresent(CommentDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CommentDO::getParentId, reqVO.getParentId())
                .eqIfPresent(CommentDO::getUid, reqVO.getUid())
                .eqIfPresent(CommentDO::getFeedbackId, reqVO.getFeedbackId())
                .eqIfPresent(CommentDO::getContent, reqVO.getContent())
                .eqIfPresent(CommentDO::getLikes, reqVO.getLikes())
                .eqIfPresent(CommentDO::getUserType, reqVO.getUserType())
                .eqIfPresent(CommentDO::getAvatar, reqVO.getAvatar())
                .likeIfPresent(CommentDO::getNickname, reqVO.getNickname())
                .orderByDesc(CommentDO::getId));
    }

    default List<CommentDO> selectList(CommentExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CommentDO>()
                .betweenIfPresent(CommentDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CommentDO::getParentId, reqVO.getParentId())
                .eqIfPresent(CommentDO::getUid, reqVO.getUid())
                .eqIfPresent(CommentDO::getFeedbackId, reqVO.getFeedbackId())
                .eqIfPresent(CommentDO::getContent, reqVO.getContent())
                .eqIfPresent(CommentDO::getLikes, reqVO.getLikes())
                .eqIfPresent(CommentDO::getUserType, reqVO.getUserType())
                .eqIfPresent(CommentDO::getAvatar, reqVO.getAvatar())
                .likeIfPresent(CommentDO::getNickname, reqVO.getNickname())
                .orderByDesc(CommentDO::getId));
    }

    default PageResult<CommentDO> selectPage(AppCommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CommentDO>()
                .eqIfPresent(CommentDO::getFeedbackId, reqVO.getFeedbackId())
                .isNull(CommentDO::getParentId)
                .orderByDesc(CommentDO::getId));
    }


    default List<CommentDO> selectByPid(Long pid) {
        return selectList(new LambdaQueryWrapperX<CommentDO>()
                .eqIfPresent(CommentDO::getParentId, pid));
    }


    default PageResult<CommentDO> selectReplyPage(PageParam pageVO, Long commentId) {
        return selectPage(pageVO, new LambdaQueryWrapperX<CommentDO>()
                .eq(CommentDO::getParentId, commentId));
    }
}
