package cn.hh.harbor.module.system.service.tenant;

import cn.hh.harbor.framework.common.util.date.LocalDateTimeUtils;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant.TenantUpdateReqVO;
import cn.hh.harbor.module.system.enums.tenant.TenantPackageDaysEnum;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hh.harbor.framework.common.enums.CommonStatusEnum;
import cn.hh.harbor.framework.common.enums.SystemIdEnum;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.common.util.collection.CollectionUtils;
import cn.hh.harbor.framework.common.util.date.DateUtils;
import cn.hh.harbor.framework.tenant.core.context.TenantContextHolder;
import cn.hh.harbor.framework.tenant.core.util.TenantUtils;
import cn.hh.harbor.module.harbor.api.feebacktag.FeedbackTagApi;
import cn.hh.harbor.module.system.controller.admin.permission.vo.role.RoleCreateReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantCreateReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantUpdateReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant.TenantExportReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant.TenantPageReqVO;
import cn.hh.harbor.module.system.convert.tenant.TenantConvert;
import cn.hh.harbor.module.system.dal.dataobject.permission.RoleDO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantDO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantPackageDO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantUserDO;
import cn.hh.harbor.module.system.dal.mysql.tenant.TenantMapper;
import cn.hh.harbor.module.system.dal.mysql.tenant.TenantUserMapper;
import cn.hh.harbor.module.system.enums.permission.RoleCodeEnum;
import cn.hh.harbor.module.system.service.permission.PermissionService;
import cn.hh.harbor.module.system.service.permission.RoleService;
import cn.hh.harbor.module.system.service.tenant.handler.TenantMenuHandler;
import cn.hh.harbor.module.system.service.token.TokenService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.hh.harbor.module.system.enums.ErrorCodeConstants.*;
import static cn.hh.harbor.module.system.enums.tenant.TenantPackageDaysEnum.NO_EXPIRATION;
import static java.util.Collections.singleton;

/**
 * 租户 Service 实现类
 */
@Service
@Validated
@Slf4j
public class TenantServiceImpl implements TenantService {

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private TenantPackageService tenantPackageService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private TenantUserService tenantUserService;

    @Resource
    private TenantUserMapper tenantUserMapper;

    @Resource
    private TokenService tokenService;

    @Resource
    private FeedbackTagApi feedbackTagApi;

    @Override
    public List<Long> getTenantIdList() {
        List<TenantDO> tenants = tenantMapper.selectList();
        return CollectionUtils.convertList(tenants, TenantDO::getId);
    }

    @Override
    public void validTenant(Long id) {
        TenantDO tenant = getTenant(id);
        if (tenant == null) {
            throw exception(TENANT_NOT_EXISTS);
        }
        if (tenant.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(TENANT_DISABLE, tenant.getName());
        }
        if (DateUtils.isExpired(tenant.getExpireTime())) {
            throw exception(TENANT_EXPIRE, tenant.getName());
        }
    }

    @Override
    public TenantDO checkTenantRouterUri(String routerUri) {
        return tenantMapper.selectOne(TenantDO::getRouterUri, routerUri);
    }

    @Transactional
    @Override
    public Long createTenant(SelectTenantCreateReqVO createReqVO, Long userId, String accessToken) {
        // 校验租户名称是否重复
        validTenantRouterUriDuplicate(createReqVO.getName(), null);
        // 创建租户
        TenantDO tenant = TenantConvert.INSTANCE.convert(createReqVO);
        TenantPackageDO tenantPackage = tenantPackageService.getGeneralTenantPackage();
        tenant.setPackageId(tenantPackage.getId());
        tenant.setStatus(CommonStatusEnum.ENABLE.getStatus());
        // 过期时间
        tenant.setExpireTime(calculateExpireTime(tenantPackage.getDays()));
        tenantMapper.insert(tenant);

        Long tenantId = tenant.getId();
        // 插入租户和用户的关联
        tenantUserService.insert(tenant.getId(), userId);
        // 创建超级租户管理员角色
        TenantUtils.execute(tenant.getId(), () -> {
            Long roleId = createRole(tenantPackage);
            // 分配角色
            permissionService.assignUserRole(userId, singleton(roleId));
        });
        // 直接刷新token,为了添加新的租户给AccessToken
        tokenService.addTenantIdByAccessToken(tenantId, accessToken);
        // 添加默认反馈标签
        feedbackTagApi.createTenantFeedbackTag(tenantId);
        return tenantId;
    }

    private Long createRole(TenantPackageDO tenantPackage) {
        // 创建角色
        Long roleId = roleService.createSuperTenantAdminRole();
        // 分配权限
        permissionService.assignRoleMenu(roleId, tenantPackage.getMenuIds());
        return roleId;
    }


    @Transactional
    @Override
    public void updateTenant(SelectTenantUpdateReqVO updateReqVO) {
        // 校验
        TenantDO tenantDO = validateUpdateTenant(updateReqVO.getId());
        // 更新租户
        TenantDO updateObj = TenantConvert.INSTANCE.convert(updateReqVO);
        tenantMapper.updateById(updateObj);
    }

    @Transactional
    @Override
    public void updateTenant(TenantUpdateReqVO updateReqVO) {
        // 校验
        TenantDO tenantDO = validateUpdateTenant(updateReqVO.getId());
        // 更新租户
        TenantDO updateObj = TenantConvert.INSTANCE.convert(updateReqVO);
        // 更新套餐时重新计算过期时间
        if (updateReqVO.getPackageId() != null) {
            TenantPackageDO tenantPackage = tenantPackageService.getTenantPackage(updateReqVO.getPackageId());
            updateObj.setExpireTime(calculateExpireTime(tenantPackage.getDays()));
        }
        tenantMapper.updateById(updateObj);
    }

    @Transactional
    @Override
    public void updateTenantRoleMenu(Long tenantId, Set<Long> menuIds) {
        TenantUtils.execute(tenantId, () -> {
            // 获得所有角色
            List<RoleDO> roles = roleService.getRoleList();
            roles.forEach(role -> Assert.isTrue(tenantId.equals(role.getTenantId()), "角色({}/{}) 租户不匹配",
                    role.getId(), role.getTenantId(), tenantId)); // 兜底校验
            // 重新分配每个角色的权限
            roles.forEach(role -> {
                // 如果是租户超级管理员，重新分配其权限为租户套餐的权限
                if (RoleCodeEnum.isSuperTenantAdmin(role.getCode())) {
                    permissionService.assignRoleMenu(role.getId(), menuIds);
                    log.info("[updateTenantRoleMenu][租户管理员({}/{}) 的权限修改为({})]", role.getId(), role.getTenantId(), menuIds);
                } else if (RoleCodeEnum.isTenantAdmin(role.getCode())) { // 如果是租户管理员，则去掉超过套餐的权限
                    Set<Long> roleMenuIds = permissionService.getRoleMenuListByRoleId(role.getId());
                    roleMenuIds = CollUtil.intersectionDistinct(roleMenuIds, menuIds);
                    permissionService.assignRoleMenu(role.getId(), roleMenuIds);
                    log.info("[updateTenantRoleMenu][角色({}/{}) 的权限修改为({})]", role.getId(), role.getTenantId(), roleMenuIds);
                }
            });
        });
    }

    @Transactional
    @Override
    public void deleteTenant(Long id) {
        // 校验存在
        TenantDO tenant = tenantMapper.selectById(id);
        if (tenant == null) {
            throw exception(TENANT_NOT_EXISTS);
        }
        // 内置租户不能删除
        if (SystemIdEnum.isSystemData(tenant.getId())) {
            throw exception(TENANT_SYSTEM_DELETE);
        }
        // 删除
        tenantMapper.deleteById(id);
        // 删除用户和租户的关联
        tenantUserMapper.deleteBatchByTenantId(id);
        // 删除租户下角色
        roleService.deleteRoleByTenantId(id);
    }

    @Override
    public TenantDO getTenant(Long id) {
        return tenantMapper.selectById(id);
    }

    @Override
    public PageResult<TenantDO> getTenantPage(TenantPageReqVO pageReqVO) {
        return tenantMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TenantDO> getTenantList(TenantExportReqVO exportReqVO) {
        return tenantMapper.selectList(exportReqVO);
    }

    @Override
    public List<TenantDO> getTenantList(Long uid) {
        List<Long> tenantIds = tenantUserMapper.selectList(TenantUserDO::getUserId, uid).stream()
                .map(TenantUserDO::getTenantId)
                .collect(Collectors.toList());
        return CollUtil.isEmpty(tenantIds) ? new ArrayList<>() : tenantMapper.selectBatchIds(tenantIds);
    }

    @Override
    public TenantDO getTenantByName(String name) {
        return tenantMapper.selectByName(name);
    }

    @Override
    public Long getTenantCountByPackageId(Long packageId) {
        return tenantMapper.selectCountByPackageId(packageId);
    }

    @Override
    public List<TenantDO> getTenantListByPackageId(Long packageId) {
        return tenantMapper.selectListByPackageId(packageId);
    }

    @Override
    public void handleTenantMenu(TenantMenuHandler handler) {
        // 获得租户，然后获得菜单
        TenantDO tenant = getTenant(TenantContextHolder.getTenantId());
        Set<Long> menuIds = tenantPackageService.getTenantPackage(tenant.getPackageId()).getMenuIds();
        // 执行处理器
        handler.handle(menuIds);
    }

    private void validTenantRouterUriDuplicate(String routerUri, Long id) {
        TenantDO tenant = tenantMapper.selectOne(TenantDO::getRouterUri, routerUri);
        if (tenant == null) {
            return;
        }
        if (id == null) {
            throw exception(TENANT_ROUTER_URI_DUPLICATE, routerUri);
        }
        if (!tenant.getId().equals(id)) {
            throw exception(TENANT_ROUTER_URI_DUPLICATE, routerUri);
        }
    }

    private TenantDO validateUpdateTenant(Long id) {
        TenantDO tenant = tenantMapper.selectById(id);
        if (tenant == null) {
            throw exception(TENANT_NOT_EXISTS);
        }
        // 内置租户不能修改
        if (SystemIdEnum.isSystemData(tenant.getId())) {
            throw exception(TENANT_SYSTEM_UPDATE);
        }
        // 校验租户路由是否重复
        validTenantRouterUriDuplicate(tenant.getRouterUri(), tenant.getId());
        return tenant;
    }

    private LocalDateTime calculateExpireTime(Integer days) {
        return days == TenantPackageDaysEnum.NO_EXPIRATION ? LocalDateTimeUtils.MAX : LocalDateTime.now().plusDays(days);
    }
}
