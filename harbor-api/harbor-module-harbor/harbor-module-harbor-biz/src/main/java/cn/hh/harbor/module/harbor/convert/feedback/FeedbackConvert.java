package cn.hh.harbor.module.harbor.convert.feedback;

import cn.hh.harbor.module.harbor.controller.admin.feedback.vo.FeedbackRespVO;
import cn.hh.harbor.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.hh.harbor.module.harbor.controller.app.feedback.vo.AppFeedbackCreateRespVO;
import cn.hh.harbor.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.hh.harbor.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.hh.harbor.module.harbor.dal.dataobject.feedback.FeedbackES;
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
    FeedbackES convert(FeedbackRespVO bean);

    AppFeedbackRespVO convertApp(FeedbackDO feedbackDO);
    AppFeedbackCreateRespVO convertApp1(FeedbackDO feedbackDO);

}
