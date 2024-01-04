package cn.hh.harbor.module.system.convert.tenant;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.packages.TenantPackageCreateReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.packages.TenantPackageRespVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.packages.TenantPackageSimpleRespVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.packages.TenantPackageUpdateReqVO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantPackageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户套餐 Convert
 */
@Mapper
public interface TenantPackageConvert {

    TenantPackageConvert INSTANCE = Mappers.getMapper(TenantPackageConvert.class);

    TenantPackageDO convert(TenantPackageCreateReqVO bean);

    TenantPackageDO convert(TenantPackageUpdateReqVO bean);

    TenantPackageRespVO convert(TenantPackageDO bean);

    List<TenantPackageRespVO> convertList(List<TenantPackageDO> list);

    List<TenantPackageSimpleRespVO> convertList2(List<TenantPackageDO> list);

    PageResult<TenantPackageRespVO> convertPage(PageResult<TenantPackageDO> page);
}
