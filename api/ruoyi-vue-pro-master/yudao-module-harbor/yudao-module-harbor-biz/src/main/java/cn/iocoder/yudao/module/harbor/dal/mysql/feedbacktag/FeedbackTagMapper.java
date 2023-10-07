package cn.iocoder.yudao.module.harbor.dal.mysql.feedbacktag;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.harbor.controller.admin.feedbacktag.vo.*;

/**
 * 反馈标签 Mapper
 *
 *  hehong
 */
@Mapper
public interface FeedbackTagMapper extends BaseMapperX<FeedbackTagDO> {

    default PageResult<FeedbackTagDO> selectPage(FeedbackTagPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackTagDO>()
                .betweenIfPresent(FeedbackTagDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackTagDO::getNameCh, reqVO.getNameCh())
                .eqIfPresent(FeedbackTagDO::getNameEn, reqVO.getNameEn())
                .eqIfPresent(FeedbackTagDO::getSort, reqVO.getSort())
                .orderByDesc(FeedbackTagDO::getId));
    }

    default List<FeedbackTagDO> selectList(FeedbackTagExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FeedbackTagDO>()
                .eqIfPresent(FeedbackTagDO::getId, reqVO.getId())
                .betweenIfPresent(FeedbackTagDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackTagDO::getNameCh, reqVO.getNameCh())
                .eqIfPresent(FeedbackTagDO::getNameEn, reqVO.getNameEn())
                .eqIfPresent(FeedbackTagDO::getSort, reqVO.getSort())
                .orderByDesc(FeedbackTagDO::getId));
    }

}
