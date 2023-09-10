package cn.iocoder.yudao.module.harbor.convert.feedback;

import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackLikeReqVO;
import cn.iocoder.yudao.module.harbor.dal.redis.feedback.FeedbackLikeRedisVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户反馈 Convert
 *
 *  芋道源码
 */
@Mapper
public interface FeedbackLikeConvert {

    FeedbackLikeConvert INSTANCE = Mappers.getMapper(FeedbackLikeConvert.class);

    FeedbackLikeRedisVO convert(AppFeedbackLikeReqVO bean);

}
