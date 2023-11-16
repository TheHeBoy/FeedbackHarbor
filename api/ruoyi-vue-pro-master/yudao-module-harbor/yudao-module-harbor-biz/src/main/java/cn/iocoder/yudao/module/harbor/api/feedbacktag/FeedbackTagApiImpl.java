package cn.iocoder.yudao.module.harbor.api.feedbacktag;

import cn.iocoder.yudao.module.harbor.api.feebacktag.FeedbackTagApi;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedbacktag.FeedbackTagDO;
import cn.iocoder.yudao.module.harbor.dal.mysql.feedbacktag.FeedbackTagMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackTagApiImpl implements FeedbackTagApi {

    @Resource
    FeedbackTagMapper feedbackTagMapper;

    @Override
    public void createTenantFeedbackTag(Long tenantId) {
        List<FeedbackTagDO> feedbackTagList = new ArrayList<FeedbackTagDO>() {{
            add(FeedbackTagDO.builder()
                    .sort(1).nameCh("问题反馈").nameEn("Problem feedback").color("#FFD700")
                    .build());
            add(FeedbackTagDO
                    .builder().sort(2).nameCh("产品建议").nameEn("Problem suggestion").color("#90EE90")
                    .build());
            add(FeedbackTagDO.builder()
                    .sort(3).nameCh("功能添加").nameEn("Function addition").color("#00CED1")
                    .build());
        }};
        // 添加租户号
        feedbackTagList.forEach(e -> e.setTenantId(tenantId));
        feedbackTagMapper.insertBatch(feedbackTagList);
    }
}
