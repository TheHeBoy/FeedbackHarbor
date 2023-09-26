package cn.iocoder.yudao.module.harbor.service.like;

import cn.hutool.core.util.RandomUtil;
import cn.iocoder.yudao.framework.test.core.ut.BaseDbAndRedisUnitTest;
import cn.iocoder.yudao.module.harbor.controller.app.like.vo.AppLikeReqVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.like.LikeDO;
import cn.iocoder.yudao.module.harbor.dal.mysql.feedback.FeedbackMapper;
import cn.iocoder.yudao.module.harbor.dal.mysql.like.LikeMapper;
import cn.iocoder.yudao.module.harbor.dal.redis.like.LikeRedisDAO;
import cn.iocoder.yudao.module.harbor.enums.like.LikeBusTypeEnum;
import cn.iocoder.yudao.module.harbor.enums.like.LikeStateEnum;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;

import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

/**
 * {@link LikeServiceImpl} 的单元测试类
 * <p>
 * hehong
 */
@Import({LikeServiceImpl.class, LikeRedisDAO.class})
public class LikeServiceImplTest extends BaseDbAndRedisUnitTest {

    @Resource
    private LikeServiceImpl likeService;


    @MockBean
    private AppUserService appUserService;


    @Resource
    private FeedbackMapper feedbackMapper;

    @Resource
    private LikeMapper likeMapper;

    /**
     * 点赞同步
     */
    @Test
    public void testSyncLike_feedback_1() {
        LikeBusTypeEnum typeEnum = LikeBusTypeEnum.FEEDBACK;

        // 生成用户
        Long userId = randomLongId();
        when(appUserService.getUser(userId)).thenReturn(randomPojo(AppUserDO.class, o -> o.setId(userId)));

        // 创建反馈
        FeedbackDO feedbackDOInsert = randomPojo(FeedbackDO.class, o -> o.setLikes(0L).setUid(userId));
        feedbackMapper.insert(feedbackDOInsert);
        Long rid = feedbackDOInsert.getId();

        // 点赞
        AppLikeReqVO appLikeReqVO = new AppLikeReqVO().setRid(rid).setBusType(typeEnum.getCode());
        likeService.like(userId, appLikeReqVO);

        // 同步点赞到数据库中
        likeService.syncLike(typeEnum);

        // 断言点赞数目
        FeedbackDO feedbackDO = feedbackMapper.selectById(rid);
        assertEquals(1L, feedbackDO.getLikes());

        // 断言点赞状态
        assertEquals(LikeStateEnum.LIKED.getCode(), likeMapper.getByUidAndRid(userId, rid).getState());
    }

    /**
     * 取消点赞同步
     */
    @Test
    public void testSyncLike_feedback_2() {
        LikeBusTypeEnum typeEnum = LikeBusTypeEnum.FEEDBACK;

        // 生成用户
        Long userId = randomLongId();
        when(appUserService.getUser(userId)).thenReturn(randomPojo(AppUserDO.class, o -> o.setId(userId)));

        // 创建反馈
        FeedbackDO feedbackDOInsert = randomPojo(FeedbackDO.class, o -> o.setLikes(1L).setUid(userId));
        feedbackMapper.insert(feedbackDOInsert);
        Long rid = feedbackDOInsert.getId();

        // 插入已经点赞的关系
        likeMapper.insert(randomPojo(LikeDO.class, o ->
                o.setState(LikeStateEnum.LIKED.getCode())
                        .setRid(rid)
                        .setUid(userId)
                        .setBusType(typeEnum.getCode())));

        // 取消点赞
        AppLikeReqVO appLikeReqVO = new AppLikeReqVO().setRid(rid).setBusType(typeEnum.getCode());
        likeService.like(userId, appLikeReqVO);

        // 同步点赞到数据库中
        likeService.syncLike(typeEnum);

        // 断言点赞数目
        FeedbackDO feedbackDO = feedbackMapper.selectById(rid);
        assertEquals(0L, feedbackDO.getLikes());

        // 断言点赞状态
        assertEquals(LikeStateEnum.LIKED_CANCEL.getCode(), likeMapper.getByUidAndRid(userId, rid).getState());
    }

}
