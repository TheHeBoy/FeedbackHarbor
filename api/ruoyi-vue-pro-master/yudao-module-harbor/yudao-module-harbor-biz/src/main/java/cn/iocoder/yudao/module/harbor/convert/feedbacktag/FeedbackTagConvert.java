package cn.iocoder.yudao.module.harbor.convert.feedbacktag;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.harbor.controller.app.feedbacktag.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.harbor.controller.admin.feedbacktag.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;

/**
 * 反馈标签 Convert
 * <p>
 * hehong
 */
@Mapper(componentModel = "spring") // 不添加会爆 bean 找不到
public interface FeedbackTagConvert {

    FeedbackTagConvert INSTANCE = Mappers.getMapper(FeedbackTagConvert.class);

    FeedbackTagDO convert(FeedbackTagCreateReqVO bean);

    FeedbackTagDO convert(FeedbackTagUpdateReqVO bean);

    FeedbackTagRespVO convert(FeedbackTagDO bean);

    AppFeedbackTagRespVO convertApp(FeedbackTagDO bean);

    List<FeedbackTagRespVO> convertList(List<FeedbackTagDO> list);

    List<AppFeedbackTagRespVO> convertListApp(List<FeedbackTagDO> list);

    PageResult<FeedbackTagRespVO> convertPage(PageResult<FeedbackTagDO> page);
}
