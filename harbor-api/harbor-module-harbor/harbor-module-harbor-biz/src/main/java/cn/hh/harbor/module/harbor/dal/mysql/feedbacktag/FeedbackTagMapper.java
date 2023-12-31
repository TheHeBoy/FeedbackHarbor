package cn.hh.harbor.module.harbor.dal.mysql.feedbacktag;

import java.util.*;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import org.apache.ibatis.annotations.Mapper;
import cn.hh.harbor.module.harbor.controller.admin.feedbacktag.vo.*;

/**
 * 反馈标签 Mapper
 * <p>
 * hehong
 */
@Mapper
public interface FeedbackTagMapper extends BaseMapperX<FeedbackTagDO> {

    default PageResult<FeedbackTagDO> selectPage(FeedbackTagPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackTagDO>()
                .betweenIfPresent(FeedbackTagDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedbackTagDO::getNameCh, reqVO.getNameCh())
                .eqIfPresent(FeedbackTagDO::getNameEn, reqVO.getNameEn())
                .orderByDesc(FeedbackTagDO::getId));
    }
}
