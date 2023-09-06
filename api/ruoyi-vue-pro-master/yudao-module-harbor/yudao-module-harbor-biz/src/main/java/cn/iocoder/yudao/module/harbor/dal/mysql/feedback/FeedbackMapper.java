package cn.iocoder.yudao.module.harbor.dal.mysql.feedback;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.*;

/**
 * 用户反馈 Mapper
 *
 *  芋道源码
 */
@Mapper
public interface FeedbackMapper extends BaseMapperX<FeedbackDO> {

    default PageResult<FeedbackDO> selectPage(FeedbackPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackDO>()
                .betweenIfPresent(FeedbackDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackDO::getUid, reqVO.getUid())
                .eqIfPresent(FeedbackDO::getContent, reqVO.getContent())
                .eqIfPresent(FeedbackDO::getLikes, reqVO.getLikes())
                .eqIfPresent(FeedbackDO::getFeedbackType, reqVO.getFeedbackType())
                .orderByDesc(FeedbackDO::getId));
    }

    default PageResult<FeedbackDO> selectPage(AppFeedbackPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackDO>()
                .betweenIfPresent(FeedbackDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackDO::getFeedbackType, reqVO.getFeedbackType())
                .orderByDesc(FeedbackDO::getId));
    }

    default List<FeedbackDO> selectList(FeedbackExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FeedbackDO>()
                .betweenIfPresent(FeedbackDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackDO::getUid, reqVO.getUid())
                .eqIfPresent(FeedbackDO::getContent, reqVO.getContent())
                .eqIfPresent(FeedbackDO::getLikes, reqVO.getLikes())
                .eqIfPresent(FeedbackDO::getFeedbackType, reqVO.getFeedbackType())
                .orderByDesc(FeedbackDO::getId));
    }

}
