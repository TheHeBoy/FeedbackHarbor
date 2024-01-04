package cn.hh.harbor.module.system.convert.mail;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.system.controller.admin.mail.vo.log.MailLogRespVO;
import cn.hh.harbor.module.system.dal.dataobject.mail.MailLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailCaptchaConvert {

    MailCaptchaConvert INSTANCE = Mappers.getMapper(MailCaptchaConvert.class);

}
