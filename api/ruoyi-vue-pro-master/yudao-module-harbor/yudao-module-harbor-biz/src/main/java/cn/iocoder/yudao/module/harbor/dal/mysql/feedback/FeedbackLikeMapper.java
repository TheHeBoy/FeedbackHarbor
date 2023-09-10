package cn.iocoder.yudao.module.harbor.dal.mysql.feedback;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.FeedbackExportReqVO;
import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.FeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackLikeReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackLikeDO;
import cn.iocoder.yudao.module.harbor.dal.redis.feedback.FeedbackLikeRedisVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 反馈点赞 Mapper
 */
@Mapper
public interface FeedbackLikeMapper extends BaseMapperX<FeedbackLikeDO> {

    default FeedbackLikeDO getByUidAndFeedbackId(FeedbackLikeRedisVO redisDO) {
        return selectOne(FeedbackLikeDO::getFeedbackId, redisDO.getFeedbackId(),
                FeedbackLikeDO::getUid, redisDO.getUid());
    }
}
