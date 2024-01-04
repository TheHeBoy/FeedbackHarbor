package cn.hh.harbor.module.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hh.harbor.framework.common.enums.CommonStatusEnum;
import cn.hh.harbor.framework.common.enums.SystemIdEnum;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.system.controller.admin.permission.vo.role.RoleCreateReqVO;
import cn.hh.harbor.module.system.controller.admin.permission.vo.role.RoleExportReqVO;
import cn.hh.harbor.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.hh.harbor.module.system.controller.admin.permission.vo.role.RoleUpdateReqVO;
import cn.hh.harbor.module.system.convert.permission.RoleConvert;
import cn.hh.harbor.module.system.dal.dataobject.permission.RoleDO;
import cn.hh.harbor.module.system.dal.mysql.permission.RoleMapper;
import cn.hh.harbor.module.system.dal.redis.RedisKeyConstants;
import cn.hh.harbor.module.system.enums.permission.RoleCodeEnum;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

import static cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.hh.harbor.framework.common.util.collection.CollectionUtils.convertList;
import static cn.hh.harbor.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.hh.harbor.module.system.enums.ErrorCodeConstants.*;

/**
 * 角色 Service 实现类
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRole(RoleCreateReqVO reqVO, Long tenantId) {
        // 校验角色
        validateRoleDuplicate(reqVO.getName(), null);
        // 插入到数据库
        RoleDO role = RoleConvert.INSTANCE.convert(reqVO);
        role.setStatus(CommonStatusEnum.ENABLE.getStatus());
        // 如果为系统租户创建的为系统管理员
        if (Objects.equals(SystemIdEnum.SYSTEM_ONLY_ID, tenantId)) {
            role.setCode(RoleCodeEnum.ADMIN.getCode());
        } else {
            role.setCode(RoleCodeEnum.TENANT_ADMIN.getCode());
        }
        roleMapper.insert(role);
        // 返回
        return role.getId();
    }

    @Override
    public Long createSuperTenantAdminRole() {
        RoleDO roleDO = new RoleDO()
                .setName(RoleCodeEnum.SUPER_TENANT_ADMIN.getName())
                .setCode(RoleCodeEnum.SUPER_TENANT_ADMIN.getCode())
                .setStatus(CommonStatusEnum.ENABLE.getStatus())
                .setRemark("系统生成")
                .setSort(0);
        roleMapper.insert(roleDO);
        return roleDO.getId();
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.ROLE, key = "#reqVO.id")
    public void updateRole(RoleUpdateReqVO reqVO) {
        // 校验是否可以更新
        validateRoleForUpdate(reqVO.getId());
        // 校验角色的唯一字段是否重复
        validateRoleDuplicate(reqVO.getName(), reqVO.getId());

        // 更新到数据库
        RoleDO updateObj = RoleConvert.INSTANCE.convert(reqVO);
        roleMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.ROLE, key = "#id")
    public void updateRoleStatus(Long id, Integer status) {
        // 校验是否可以更新
        validateRoleForUpdate(id);

        // 更新状态
        RoleDO updateObj = new RoleDO().setId(id).setStatus(status);
        roleMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKeyConstants.ROLE, key = "#id")
    public void deleteRole(Long id) {
        // 校验是否可以更新
        validateRoleForUpdate(id);
        // 标记删除
        roleMapper.deleteById(id);
        // 删除相关数据
        permissionService.processRoleDeleted(id);
    }

    @Transactional
    @Override
    public void deleteRoleByTenantId(Long tenantId) {
        roleMapper.selectListByTenantId(tenantId).forEach(e -> {
            getSelf().deleteRole(e.getId());
        });
    }

    /**
     * 校验角色的唯一字段是否重复
     * <p>
     * 是否存在相同名字的角色
     *
     * @param name 角色名字
     * @param id   角色编号
     */
    @VisibleForTesting
    void validateRoleDuplicate(String name, Long id) {
        // 该 name 名字被其它角色所使用
        RoleDO role = roleMapper.selectByName(name);
        if (role != null && !role.getId().equals(id)) {
            throw exception(ROLE_NAME_DUPLICATE, name);
        }
    }

    /**
     * 校验角色是否可以被更新
     *
     * @param id 角色编号
     */
    @VisibleForTesting
    void validateRoleForUpdate(Long id) {
        RoleDO roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw exception(ROLE_NOT_EXISTS);
        }
        // 内置角色,不能操作
        if (SystemIdEnum.isSystemData(id)) {
            throw exception(ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

    @Override
    public RoleDO getRole(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    @Cacheable(value = RedisKeyConstants.ROLE, key = "#id", unless = "#result == null")
    public RoleDO getRoleFromCache(Long id) {
        return roleMapper.selectById(id);
    }


    @Override
    public List<RoleDO> getRoleListByStatus(Collection<Integer> statuses) {
        return roleMapper.selectListByStatus(statuses);
    }

    @Override
    public List<RoleDO> getRoleList() {
        return roleMapper.selectList();
    }

    @Override
    public List<RoleDO> getRoleList(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return roleMapper.selectBatchIds(ids);
    }

    @Override
    public List<RoleDO> getRoleListFromCache(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        // 这里采用 for 循环从缓存中获取，主要考虑 Spring CacheManager 无法批量操作的问题
        RoleServiceImpl self = getSelf();
        return convertList(ids, self::getRoleFromCache);
    }

    @Override
    public PageResult<RoleDO> getRolePage(RolePageReqVO reqVO) {
        return roleMapper.selectPage(reqVO);
    }


    @Override
    public boolean hasAnySuperAdmin(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return false;
        }
        RoleServiceImpl self = getSelf();
        return ids.stream().anyMatch(id -> {
            RoleDO role = self.getRoleFromCache(id);
            return role != null && RoleCodeEnum.isSuperAdmin(role.getCode());
        });
    }

    @Override
    public boolean hasAnySuperTenantAdmin(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return false;
        }
        RoleServiceImpl self = getSelf();
        return ids.stream().anyMatch(id -> {
            RoleDO role = self.getRoleFromCache(id);
            return role != null && RoleCodeEnum.isSuperTenantAdmin(role.getCode());
        });
    }

    @Override
    public void validateRoleList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得角色信息
        List<RoleDO> roles = roleMapper.selectBatchIds(ids);
        Map<Long, RoleDO> roleMap = convertMap(roles, RoleDO::getId);
        // 校验
        ids.forEach(id -> {
            RoleDO role = roleMap.get(id);
            if (role == null) {
                throw exception(ROLE_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())) {
                throw exception(ROLE_IS_DISABLE, role.getName());
            }
        });
    }

    /**
     * 获得自身的代理对象，解决 AOP 生效问题
     *
     * @return 自己
     */
    private RoleServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
