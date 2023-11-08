package cn.iocoder.yudao.module.system.service.token;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.token.vo.AccessTokenPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.token.TokenAccessDO;

import java.util.List;

/**
 * Token Service 接口
 */
public interface TokenService {

    /**
     * 创建访问令牌
     *
     * @param userId              用户编号
     * @param userType            用户类型
     * @param tenantIds             授权的社区租户
     * @param isRequireRefreshToken 是否需要刷新token
     * @return 访问令牌的信息
     */
    TokenAccessDO createAccessToken(Long userId, Integer userType, List<Long> tenantIds, boolean isRequireRefreshToken);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 访问令牌的信息
     */
    TokenAccessDO refreshAccessToken(String refreshToken);

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
     * 注意：该流程中，会移除相关的刷新令牌
     * 参考 DefaultTokenServices 的 revokeToken 方法
     *
     * @param accessToken 刷新令牌
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

}
