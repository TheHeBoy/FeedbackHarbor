package cn.iocoder.yudao.module.harbor.service.feedback;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.*;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 用户反馈 Service 接口
 * <p>
 */
public interface FeedbackService {
    /**
     * 删除用户反馈
     *
     * @param id 编号
     */
    void deleteFeedback(Long id);


    /**
     * 获得用户反馈分页
     *
     * @param pageReqVO 分页查询
     * @return 用户反馈分页
     */
    PageResult<FeedbackRespVO> getFeedbackPage(FeedbackPageReqVO pageReqVO);


    AppFeedbackRespVO createFeedback(AppFeedbackCreateReqVO createReqVO, Long uid);

    PageResult<AppFeedbackRespVO> getFeedbackPage(AppFeedbackPageReqVO pageVO);
}
