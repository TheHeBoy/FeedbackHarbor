package cn.hh.harbor.module.system.service.permission;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.system.controller.admin.permission.vo.role.RoleCreateReqVO;
import cn.hh.harbor.module.system.controller.admin.permission.vo.role.RoleExportReqVO;
import cn.hh.harbor.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.hh.harbor.module.system.controller.admin.permission.vo.role.RoleUpdateReqVO;
import cn.hh.harbor.module.system.dal.dataobject.permission.RoleDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色 Service 接口
 *
 *
 */
public interface RoleService {

    /**
     * 创建角色
     *
     * @param reqVO 创建角色信息
     * @return 角色编号
     */
    Long createRole(@Valid RoleCreateReqVO reqVO, Long tenantId);

    /**
     * 在当前租户下创建超级租户管理角色
     *
     * @return 角色编号
     */
    Long createSuperTenantAdminRole();

    /**
     * 更新角色
     *
     * @param reqVO 更新角色信息
     */
    void updateRole(@Valid RoleUpdateReqVO reqVO);

    /**
     * 删除角色
     *
     * @param id 角色编号
     */
    void deleteRole(Long id);

    /**
     * 通过租户 id 删除角色
     *
     * @param tenantId 租户id
     */
    void deleteRoleByTenantId(Long tenantId);

    /**
     * 更新角色状态
     *
     * @param id 角色编号
     * @param status 状态
     */
    void updateRoleStatus(Long id, Integer status);

    /**
     * 获得角色
     *
     * @param id 角色编号
     * @return 角色
     */
    RoleDO getRole(Long id);

    /**
     * 获得角色，从缓存中
     *
     * @param id 角色编号
     * @return 角色
     */
    RoleDO getRoleFromCache(Long id);

    /**
     * 获得角色列表
     *
     * @param ids 角色编号数组
     * @return 角色列表
     */
    List<RoleDO> getRoleList(Collection<Long> ids);

    /**
     * 获得角色数组，从缓存中
     *
     * @param ids 角色编号数组
     * @return 角色数组
     */
    List<RoleDO> getRoleListFromCache(Collection<Long> ids);

    /**
     * 获得角色列表
     *
     * @param statuses 筛选的状态
     * @return 角色列表
     */
    List<RoleDO> getRoleListByStatus(Collection<Integer> statuses);

    /**
     * 获得所有角色列表
     *
     * @return 角色列表
     */
    List<RoleDO> getRoleList();

    /**
     * 获得角色分页
     *
     * @param reqVO 角色分页查询
     * @return 角色分页结果
     */
    PageResult<RoleDO> getRolePage(RolePageReqVO reqVO);

    /**
     * 获得角色列表
     *
     * @param reqVO 列表查询
     * @return 角色列表
     */
    List<RoleDO> getRoleList(RoleExportReqVO reqVO);

    /**
     * 判断角色编号数组中，是否有超级管理员
     *
     * @param ids 角色编号数组
     * @return 是否有管理员
     */
    boolean hasAnySuperAdmin(Collection<Long> ids);

    /**
     * 判断角色编号数组中，是否有超级租户管理员
     *
     * @param ids 角色编号数组
     * @return 是否有管理员
     */
    boolean hasAnySuperTenantAdmin(Collection<Long> ids);

    /**
     * 校验角色们是否有效。如下情况，视为无效：
     * 1. 角色编号不存在
     * 2. 角色被禁用
     *
     * @param ids 角色编号数组
     */
    void validateRoleList(Collection<Long> ids);

}
