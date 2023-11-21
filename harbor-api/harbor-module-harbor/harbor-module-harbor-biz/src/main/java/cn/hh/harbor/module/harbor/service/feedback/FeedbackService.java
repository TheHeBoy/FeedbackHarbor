package cn.hh.harbor.module.harbor.service.feedback;

import java.util.*;
import javax.validation.*;

import cn.hh.harbor.module.harbor.controller.admin.feedback.vo.*;
import cn.hh.harbor.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.hh.harbor.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.hh.harbor.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.hh.harbor.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.harbor.enums.feedback.FeedbackReplyStateEnum;

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
