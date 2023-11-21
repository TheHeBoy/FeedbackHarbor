package cn.hh.harbor.module.system.convert.logger;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.hh.harbor.module.system.controller.admin.logger.vo.loginlog.LoginLogExcelVO;
import cn.hh.harbor.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import cn.hh.harbor.module.system.dal.dataobject.logger.LoginLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogDO> page);

    List<LoginLogExcelVO> convertList(List<LoginLogDO> list);

    LoginLogDO convert(LoginLogCreateReqDTO bean);

}
