package cn.iocoder.yudao.module.harbor.convert.feedback;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;

/**
 * 用户反馈 Convert
 *
 *  芋道源码
 */
@Mapper
public interface FeedbackConvert {

    FeedbackConvert INSTANCE = Mappers.getMapper(FeedbackConvert.class);

    FeedbackDO convert(FeedbackCreateReqVO bean);

    FeedbackDO convert(AppFeedbackCreateReqVO bean);

    FeedbackDO convert(FeedbackUpdateReqVO bean);

    FeedbackRespVO convert(FeedbackDO bean);

    List<FeedbackRespVO> convertList(List<FeedbackDO> list);

    PageResult<FeedbackRespVO> convertPage(PageResult<FeedbackDO> page);

    PageResult<AppFeedbackRespVO> convertPageApp(PageResult<FeedbackDO> page);

    List<FeedbackExcelVO> convertList02(List<FeedbackDO> list);

}
