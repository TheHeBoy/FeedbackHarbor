package cn.hh.harbor.module.harbor.api.feebacktag;


/**
 * 反馈标签 API 接口
 */
public interface FeedbackTagApi {


    /**
     * 为租户社区创建默认反馈标签
     */
    void createTenantFeedbackTag(Long tenantId);
}