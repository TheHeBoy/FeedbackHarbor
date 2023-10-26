package cn.iocoder.yudao.module.harbor.convert.feedback;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;

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
