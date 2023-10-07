package cn.iocoder.yudao.module.harbor.service.feedbacktag;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.harbor.controller.admin.feedbacktag.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import cn.iocoder.yudao.module.harbor.dal.mysql.feedbacktag.FeedbackTagMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link FeedbackTagServiceImpl} 的单元测试类
 *
 *  hehong
 */
@Import(FeedbackTagServiceImpl.class)
public class FeedbackTagServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FeedbackTagServiceImpl feedbackTagService;

    @Resource
    private FeedbackTagMapper feedbackTagMapper;

    @Test
    public void testCreateFeedbackTag_success() {
        // 准备参数
        FeedbackTagCreateReqVO reqVO = randomPojo(FeedbackTagCreateReqVO.class);

        // 调用
        Long feedbackTagId = feedbackTagService.createFeedbackTag(reqVO);
        // 断言
        assertNotNull(feedbackTagId);
        // 校验记录的属性是否正确
        FeedbackTagDO feedbackTag = feedbackTagMapper.selectById(feedbackTagId);
        assertPojoEquals(reqVO, feedbackTag);
    }

    @Test
    public void testUpdateFeedbackTag_success() {
        // mock 数据
        FeedbackTagDO dbFeedbackTag = randomPojo(FeedbackTagDO.class);
        feedbackTagMapper.insert(dbFeedbackTag);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FeedbackTagUpdateReqVO reqVO = randomPojo(FeedbackTagUpdateReqVO.class, o -> {
            o.setId(dbFeedbackTag.getId()); // 设置更新的 ID
        });

        // 调用
        feedbackTagService.updateFeedbackTag(reqVO);
        // 校验是否更新正确
        FeedbackTagDO feedbackTag = feedbackTagMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, feedbackTag);
    }

    @Test
    public void testUpdateFeedbackTag_notExists() {
        // 准备参数
        FeedbackTagUpdateReqVO reqVO = randomPojo(FeedbackTagUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> feedbackTagService.updateFeedbackTag(reqVO), FEEDBACK_TAG_NOT_EXISTS);
    }

    @Test
    public void testDeleteFeedbackTag_success() {
        // mock 数据
        FeedbackTagDO dbFeedbackTag = randomPojo(FeedbackTagDO.class);
        feedbackTagMapper.insert(dbFeedbackTag);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFeedbackTag.getId();

        // 调用
        feedbackTagService.deleteFeedbackTag(id);
       // 校验数据不存在了
       assertNull(feedbackTagMapper.selectById(id));
    }

    @Test
    public void testDeleteFeedbackTag_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> feedbackTagService.deleteFeedbackTag(id), FEEDBACK_TAG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFeedbackTagPage() {
       // mock 数据
       FeedbackTagDO dbFeedbackTag = randomPojo(FeedbackTagDO.class, o -> { // 等会查询到
           o.setId(null);
           o.setCreateTime(null);
           o.setNameCh(null);
           o.setNameEn(null);
           o.setSort(null);
       });
       feedbackTagMapper.insert(dbFeedbackTag);
       // 测试 id 不匹配
       feedbackTagMapper.insert(cloneIgnoreId(dbFeedbackTag, o -> o.setId(null)));
       // 测试 createTime 不匹配
       feedbackTagMapper.insert(cloneIgnoreId(dbFeedbackTag, o -> o.setCreateTime(null)));
       // 测试 nameCh 不匹配
       feedbackTagMapper.insert(cloneIgnoreId(dbFeedbackTag, o -> o.setNameCh(null)));
       // 测试 nameEn 不匹配
       feedbackTagMapper.insert(cloneIgnoreId(dbFeedbackTag, o -> o.setNameEn(null)));
       // 测试 order 不匹配
       feedbackTagMapper.insert(cloneIgnoreId(dbFeedbackTag, o -> o.setSort(null)));
       // 准备参数
       FeedbackTagPageReqVO reqVO = new FeedbackTagPageReqVO();
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setNameCh(null);
       reqVO.setNameEn(null);
       reqVO.setSort(null);

       // 调用
       PageResult<FeedbackTagDO> pageResult = feedbackTagService.getFeedbackTagPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbFeedbackTag, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFeedbackTagList() {
       // mock 数据
       FeedbackTagDO dbFeedbackTag = randomPojo(FeedbackTagDO.class, o -> { // 等会查询到
           o.setId(null);
           o.setCreateTime(null);
           o.setNameCh(null);
           o.setNameEn(null);
           o.setSort(null);
       });
       feedbackTagMapper.insert(dbFeedbackTag);
       // 测试 id 不匹配
       feedbackTagMapper.insert(cloneIgnoreId(dbFeedbackTag, o -> o.setId(null)));
       // 测试 createTime 不匹配
       feedbackTagMapper.insert(cloneIgnoreId(dbFeedbackTag, o -> o.setCreateTime(null)));
       // 测试 nameCh 不匹配
       feedbackTagMapper.insert(cloneIgnoreId(dbFeedbackTag, o -> o.setNameCh(null)));
       // 测试 nameEn 不匹配
       feedbackTagMapper.insert(cloneIgnoreId(dbFeedbackTag, o -> o.setNameEn(null)));
       // 测试 order 不匹配
       feedbackTagMapper.insert(cloneIgnoreId(dbFeedbackTag, o -> o.setSort(null)));
       // 准备参数
       FeedbackTagExportReqVO reqVO = new FeedbackTagExportReqVO();
       reqVO.setId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setNameCh(null);
       reqVO.setNameEn(null);
       reqVO.setSort(null);

       // 调用
       List<FeedbackTagDO> list = feedbackTagService.getFeedbackTagList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbFeedbackTag, list.get(0));
    }

}
