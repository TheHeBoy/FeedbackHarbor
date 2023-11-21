package cn.hh.harbor.module.system.convert.sms;

import cn.hh.harbor.module.system.controller.admin.sms.vo.channel.SmsChannelCreateReqVO;
import cn.hh.harbor.module.system.controller.admin.sms.vo.channel.SmsChannelRespVO;
import cn.hh.harbor.module.system.controller.admin.sms.vo.channel.SmsChannelSimpleRespVO;
import cn.hh.harbor.module.system.controller.admin.sms.vo.channel.SmsChannelUpdateReqVO;
import cn.hh.harbor.module.system.dal.dataobject.sms.SmsChannelDO;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.sms.core.property.SmsChannelProperties;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信渠道 Convert
 *
 *
 */
@Mapper
public interface SmsChannelConvert {

    SmsChannelConvert INSTANCE = Mappers.getMapper(SmsChannelConvert.class);

    SmsChannelDO convert(SmsChannelCreateReqVO bean);

    SmsChannelDO convert(SmsChannelUpdateReqVO bean);

    SmsChannelRespVO convert(SmsChannelDO bean);

    List<SmsChannelRespVO> convertList(List<SmsChannelDO> list);

    PageResult<SmsChannelRespVO> convertPage(PageResult<SmsChannelDO> page);

    List<SmsChannelProperties> convertList02(List<SmsChannelDO> list);

    List<SmsChannelSimpleRespVO> convertList03(List<SmsChannelDO> list);

}
