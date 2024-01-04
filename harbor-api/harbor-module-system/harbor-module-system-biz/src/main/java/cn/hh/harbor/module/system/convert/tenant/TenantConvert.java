package cn.hh.harbor.module.system.convert.tenant;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.system.api.tenant.dto.TenantRespDTO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantCreateReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantRespVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantUpdateReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant.TenantCreateReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant.TenantExcelVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant.TenantRespVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant.TenantUpdateReqVO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户 Convert
 */
@Mapper
public interface TenantConvert {

    TenantConvert INSTANCE = Mappers.getMapper(TenantConvert.class);

    TenantDO convert(TenantCreateReqVO bean);
    TenantDO convert(SelectTenantUpdateReqVO bean);
    TenantDO convert(SelectTenantCreateReqVO bean);

    TenantDO convert(TenantUpdateReqVO bean);

    TenantRespVO convert(TenantDO bean);
    TenantRespDTO convertDTO(TenantDO bean);

    SelectTenantRespVO convertSelect(TenantDO bean);

    List<TenantRespVO> convertList(List<TenantDO> list);
    List<SelectTenantRespVO> convertListSelect(List<TenantDO> list);

    PageResult<TenantRespVO> convertPage(PageResult<TenantDO> page);

    List<TenantExcelVO> convertList02(List<TenantDO> list);

}
