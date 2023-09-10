package cn.iocoder.yudao.module.harbor.service.feedback;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbAndRedisUnitTest;
import cn.iocoder.yudao.framework.test.core.ut.BaseRedisUnitTest;
import cn.iocoder.yudao.framework.test.core.util.AssertUtils;
import cn.iocoder.yudao.framework.test.core.util.RandomUtils;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackLikeReqVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.harbor.dal.redis.feedback.FeedbackLikeRedisDAO;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserService;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomSet;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link FeedbackServiceLikeImpl} 的单元测试类
 * <p>
 * hehong
 */
@Import({FeedbackServiceLikeImpl.class, FeedbackServiceImpl.class, FeedbackLikeRedisDAO.class, AppUserServiceImpl.class})
public class FeedbackServiceLikeImplTest extends BaseDbAndRedisUnitTest {

    @Resource
    private FeedbackServiceLikeImpl feedbackServiceLike;

    @Resource
    private FeedbackServiceImpl feedbackService;

    /**
     * 测试用户
     */
    private static final Long uid = 1L;

    /**
     * 点赞功能
     */
    @Test
    public void testLike() {
        // 插入一条反馈
        AppFeedbackCreateReqVO appFeedbackCreateReqVO = randomPojo(AppFeedbackCreateReqVO.class);
        FeedbackDO feedbackDO = feedbackService.createFeedback(appFeedbackCreateReqVO, uid);

        //对这条反馈进行点赞
        AppFeedbackLikeReqVO appFeedbackLikeReqVO = new AppFeedbackLikeReqVO();
        appFeedbackLikeReqVO.setFeedbackId(feedbackDO.getId());
        feedbackServiceLike.like(appFeedbackLikeReqVO, uid);

        FeedbackDO feedback = feedbackService.getFeedback(appFeedbackLikeReqVO.getFeedbackId());
        assertEquals(1, feedback.getLikes());
    }


    /**
     * 取消点赞
     */
    @Test
    public void testLikeCancel() {
        //点赞
        AppFeedbackCreateReqVO appFeedbackCreateReqVO = randomPojo(AppFeedbackCreateReqVO.class);
        FeedbackDO feedbackDO = feedbackService.createFeedback(appFeedbackCreateReqVO, uid);
        AppFeedbackLikeReqVO appFeedbackLikeReqVO = new AppFeedbackLikeReqVO();
        appFeedbackLikeReqVO.setFeedbackId(feedbackDO.getId());

        feedbackServiceLike.like(appFeedbackLikeReqVO, uid);
        FeedbackDO feedback = feedbackService.getFeedback(appFeedbackLikeReqVO.getFeedbackId());
        assertEquals(1, feedback.getLikes());

        //取消点赞
        feedbackServiceLike.like(appFeedbackLikeReqVO, uid);
        FeedbackDO feedbackCancel = feedbackService.getFeedback(appFeedbackLikeReqVO.getFeedbackId());
        assertEquals(0, feedbackCancel.getLikes());

    }


    /**
     * 点赞 -> 取消点赞 -> 点赞
     */
    @Test
    public void testLikeCancel_like() {
        //点赞
        AppFeedbackCreateReqVO appFeedbackCreateReqVO = randomPojo(AppFeedbackCreateReqVO.class);
        FeedbackDO feedbackDO = feedbackService.createFeedback(appFeedbackCreateReqVO, uid);
        AppFeedbackLikeReqVO appFeedbackLikeReqVO = new AppFeedbackLikeReqVO();
        appFeedbackLikeReqVO.setFeedbackId(feedbackDO.getId());

        feedbackServiceLike.like(appFeedbackLikeReqVO, uid);
        FeedbackDO feedback = feedbackService.getFeedback(appFeedbackLikeReqVO.getFeedbackId());
        assertEquals(1, feedback.getLikes());

        //取消点赞
        feedbackServiceLike.like(appFeedbackLikeReqVO, uid);
        FeedbackDO feedbackCancel = feedbackService.getFeedback(appFeedbackLikeReqVO.getFeedbackId());
        assertEquals(0, feedbackCancel.getLikes());

        //点赞
        feedbackServiceLike.like(appFeedbackLikeReqVO, uid);
        assertEquals(1, feedbackService.getFeedback(appFeedbackLikeReqVO.getFeedbackId()).getLikes());
    }

    @Test
    public void testListByUid() {
        //随机创建反馈
        Set<AppFeedbackCreateReqVO> appFeedbackCreateReqVOS = randomSet(AppFeedbackCreateReqVO.class);
        Set<FeedbackDO> feedbackDOS = appFeedbackCreateReqVOS.stream().map(e -> feedbackService.createFeedback(e, uid)).collect(Collectors.toSet());

        //给每个反馈点赞
        for (FeedbackDO feedbackDO : feedbackDOS) {
            AppFeedbackLikeReqVO appFeedbackLikeReqVO = new AppFeedbackLikeReqVO();
            appFeedbackLikeReqVO.setFeedbackId(feedbackDO.getId());
            feedbackServiceLike.like(appFeedbackLikeReqVO, uid);
        }

        //得到上面点赞过的反馈
        Set<Long> feedbackIds = feedbackServiceLike.listByUid(uid);

        assertEquals(feedbackDOS.size(), feedbackIds.size());

        assertTrue(feedbackIds.containsAll(feedbackDOS.stream().map(FeedbackDO::getId).collect(Collectors.toSet())));
    }
}
