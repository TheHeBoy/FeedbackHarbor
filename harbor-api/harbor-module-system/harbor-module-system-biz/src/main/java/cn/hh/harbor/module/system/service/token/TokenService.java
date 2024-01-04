package cn.hh.harbor.module.system.service.token;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.system.controller.admin.token.vo.AccessTokenPageReqVO;
import cn.hh.harbor.module.system.dal.dataobject.token.TokenAccessDO;

import java.util.List;

/**
 * Token Service 接口
 */
public interface TokenService {

    /**
     * 创建访问令牌
     *
     * @param userId              用户编号
     * @param tenantIds             授权的社区租户
     * @return 访问令牌的信息
     */
    TokenAccessDO createAccessToken(Long userId, List<Long> tenantIds);

    /**
     * 获得访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    TokenAccessDO getAccessToken(String accessToken);

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    TokenAccessDO checkAccessToken(String accessToken);

    /**
     * 移除访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    TokenAccessDO removeAccessToken(String accessToken);

    /**
     * 获得访问令牌分页
     *
     * @param reqVO 请求
     * @return 访问令牌分页
     */
    PageResult<TokenAccessDO> getAccessTokenPage(AccessTokenPageReqVO reqVO);

    /**
     * 添加 tenant 到 accessToken 的 tenantIds中
     * @param accessToken 访问令牌
     */
    void addTenantIdByAccessToken(Long tenantId,String accessToken);
}
