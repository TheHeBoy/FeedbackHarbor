package cn.hh.harbor.module.system.dal.mysql.sms;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.module.system.controller.admin.sms.vo.template.SmsTemplateExportReqVO;
import cn.hh.harbor.module.system.controller.admin.sms.vo.template.SmsTemplatePageReqVO;
import cn.hh.harbor.module.system.dal.dataobject.sms.SmsTemplateDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SmsTemplateMapper extends BaseMapperX<SmsTemplateDO> {

    default SmsTemplateDO selectByCode(String code) {
        return selectOne(SmsTemplateDO::getCode, code);
    }

    default PageResult<SmsTemplateDO> selectPage(SmsTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SmsTemplateDO>()
                .eqIfPresent(SmsTemplateDO::getType, reqVO.getType())
                .eqIfPresent(SmsTemplateDO::getStatus, reqVO.getStatus())
                .likeIfPresent(SmsTemplateDO::getCode, reqVO.getCode())
                .likeIfPresent(SmsTemplateDO::getContent, reqVO.getContent())
                .likeIfPresent(SmsTemplateDO::getApiTemplateId, reqVO.getApiTemplateId())
                .eqIfPresent(SmsTemplateDO::getChannelId, reqVO.getChannelId())
                .betweenIfPresent(SmsTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SmsTemplateDO::getId));
    }

    default List<SmsTemplateDO> selectList(SmsTemplateExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SmsTemplateDO>()
                .eqIfPresent(SmsTemplateDO::getType, reqVO.getType())
                .eqIfPresent(SmsTemplateDO::getStatus, reqVO.getStatus())
                .likeIfPresent(SmsTemplateDO::getCode, reqVO.getCode())
                .likeIfPresent(SmsTemplateDO::getContent, reqVO.getContent())
                .likeIfPresent(SmsTemplateDO::getApiTemplateId, reqVO.getApiTemplateId())
                .eqIfPresent(SmsTemplateDO::getChannelId, reqVO.getChannelId())
                .betweenIfPresent(SmsTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SmsTemplateDO::getId));
    }

    default Long selectCountByChannelId(Long channelId) {
        return selectCount(SmsTemplateDO::getChannelId, channelId);
    }

}
