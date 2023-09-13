package cn.iocoder.yudao.module.harbor.service.feedback;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.FeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.FeedbackExportReqVO;
import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.FeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo.FeedbackUpdateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackLikeReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 用户反馈 Service 接口
 *
 *
 */
public interface FeedbackLikeService {


    /**
     * 点赞或取消点赞
     * @param likeReqVO 点赞参数
     * @return boolean
     */
    boolean like(AppFeedbackLikeReqVO likeReqVO, Long uid);

    /**
     * 通过用户id得到所有点赞的反馈
     * @param uid 用户id
     * @return {@link List}<{@link Long}>
     */
    Set<Long> listByUid(Long uid);


    /**
     * 同步缓存中的反馈点赞数据到数据库中
     */
    void syncLike();
}
