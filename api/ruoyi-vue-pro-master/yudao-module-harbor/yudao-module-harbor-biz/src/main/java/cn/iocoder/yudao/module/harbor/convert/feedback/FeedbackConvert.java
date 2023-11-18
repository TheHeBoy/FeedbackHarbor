package cn.iocoder.yudao.module.harbor.convert.feedback;

import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.FeedbackRespVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户反馈 Convert
 */
@Mapper
public interface FeedbackConvert {

    FeedbackConvert INSTANCE = Mappers.getMapper(FeedbackConvert.class);

    FeedbackDO convert(AppFeedbackCreateReqVO bean);

    FeedbackRespVO convert(FeedbackDO bean);

    AppFeedbackRespVO convertApp(FeedbackDO feedbackDO);

}
