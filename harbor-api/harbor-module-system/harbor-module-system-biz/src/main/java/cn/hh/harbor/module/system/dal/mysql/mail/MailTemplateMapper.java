package cn.hh.harbor.module.system.dal.mysql.mail;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.module.system.controller.admin.mail.vo.template.MailTemplatePageReqVO;
import cn.hh.harbor.module.system.dal.dataobject.mail.MailTemplateDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailTemplateMapper extends BaseMapperX<MailTemplateDO> {

    default PageResult<MailTemplateDO> selectPage(MailTemplatePageReqVO pageReqVO){
        return selectPage(pageReqVO , new LambdaQueryWrapperX<MailTemplateDO>()
                .eqIfPresent(MailTemplateDO::getStatus, pageReqVO.getStatus())
                .likeIfPresent(MailTemplateDO::getCode, pageReqVO.getCode())
                .likeIfPresent(MailTemplateDO::getName, pageReqVO.getName())
                .eqIfPresent(MailTemplateDO::getAccountId, pageReqVO.getAccountId())
                .betweenIfPresent(MailTemplateDO::getCreateTime, pageReqVO.getCreateTime()));
    }

    default Long selectCountByAccountId(Long accountId) {
        return selectCount(MailTemplateDO::getAccountId, accountId);
    }

    default MailTemplateDO selectByCode(String code) {
        return selectOne(MailTemplateDO::getCode, code);
    }

}
