package cn.iocoder.yudao.module.harbor.service.feedbacktag;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.harbor.controller.admin.feedbacktag.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.harbor.convert.feedbacktag.FeedbackTagConvert;
import cn.iocoder.yudao.module.harbor.dal.mysql.feedbacktag.FeedbackTagMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.*;

/**
 * 反馈标签 Service 实现类
 *
 *  hehong
 */
@Service
@Validated
public class FeedbackTagServiceImpl implements FeedbackTagService {

    @Resource
    private FeedbackTagMapper feedbackTagMapper;

    @Override
    public Long createFeedbackTag(FeedbackTagCreateReqVO createReqVO) {
        // 插入
        FeedbackTagDO feedbackTag = FeedbackTagConvert.INSTANCE.convert(createReqVO);
        feedbackTagMapper.insert(feedbackTag);
        // 返回
        return feedbackTag.getId();
    }

    @Override
    public void updateFeedbackTag(FeedbackTagUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedbackTagExists(updateReqVO.getId());
        // 更新
        FeedbackTagDO updateObj = FeedbackTagConvert.INSTANCE.convert(updateReqVO);
        feedbackTagMapper.updateById(updateObj);
    }

    @Override
    public void deleteFeedbackTag(Long id) {
        // 校验存在
        validateFeedbackTagExists(id);
        // 删除
        feedbackTagMapper.deleteById(id);
    }

    private void validateFeedbackTagExists(Long id) {
        if (feedbackTagMapper.selectById(id) == null) {
            throw exception(FEEDBACK_TAG_NOT_EXISTS);
        }
    }

    @Override
    public FeedbackTagDO getFeedbackTag(Long id) {
        return feedbackTagMapper.selectById(id);
    }

    @Override
    public List<FeedbackTagDO> getFeedbackTagList(Collection<Long> ids) {
        return feedbackTagMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FeedbackTagDO> getFeedbackTagPage(FeedbackTagPageReqVO pageReqVO) {
        return feedbackTagMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FeedbackTagDO> getFeedbackTagList(FeedbackTagExportReqVO exportReqVO) {
        return feedbackTagMapper.selectList(exportReqVO);
    }

    @Override
    public List<FeedbackTagDO> getFeedbackTagList() {
        return feedbackTagMapper.selectList();
    }

}
