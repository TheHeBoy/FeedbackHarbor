package cn.iocoder.yudao.module.system.service.token;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;
import cn.iocoder.yudao.module.system.controller.admin.token.vo.AccessTokenPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.token.TokenAccessDO;
import cn.iocoder.yudao.module.system.dal.dataobject.token.TokenRefreshDO;
import cn.iocoder.yudao.module.system.dal.mysql.token.TokenAccessTokenMapper;
import cn.iocoder.yudao.module.system.dal.mysql.token.TokenRefreshTokenMapper;
import cn.iocoder.yudao.module.system.dal.redis.token.AccessTokenRedisDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * Token Service 实现类
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private TokenAccessTokenMapper tokenAccessTokenMapper;
    @Resource
    private TokenRefreshTokenMapper tokenRefreshTokenMapper;
    @Resource
    private AccessTokenRedisDAO accessTokenRedisDAO;

    @Override
    @Transactional
    public TokenAccessDO createAccessToken(Long userId, Integer userType, List<Long> tenantIds, boolean isRequireRefreshToken) {
        // 创建 refresh 令牌
        String refreshToken = isRequireRefreshToken ? createRefreshToken(userId, userType).getRefreshToken() : null;
        // 创建访问令牌
        TokenAccessDO accessTokenDO = new TokenAccessDO().setAccessToken(generateAccessToken())
                .setUserId(userId)
                .setUserType(userType)
                .setRefreshToken(refreshToken)
                .setTenantIds(tenantIds)
                .setExpiresTime(LocalDateTime.now().plusSeconds(360000)); // todo 配置过期时间
        tokenAccessTokenMapper.insert(accessTokenDO);
        // 记录到 Redis 中
        accessTokenRedisDAO.set(accessTokenDO);
        return accessTokenDO;
    }

    @Override
    public TokenAccessDO refreshAccessToken(String refreshToken) {
        // 查询访问令牌
        TokenRefreshDO refreshTokenDO = tokenRefreshTokenMapper.selectByRefreshToken(refreshToken);
        if (refreshTokenDO == null) {
            throw exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "无效的刷新令牌");
        }

        // 移除相关的访问令牌
        TokenAccessDO accessTokenDO = tokenAccessTokenMapper.selectByRefreshToken(refreshToken);
        if (ObjectUtil.isNotNull(accessTokenDO)) {
            tokenAccessTokenMapper.deleteById(accessTokenDO.getId());
            accessTokenRedisDAO.delete(accessTokenDO.getAccessToken());
        }

        // 已过期的情况下，删除刷新令牌
        if (DateUtils.isExpired(refreshTokenDO.getExpiresTime())) {
            tokenRefreshTokenMapper.deleteById(refreshTokenDO.getId());
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "刷新令牌已过期");
        }

        // 创建访问令牌
        return getSelf().createAccessToken(
                refreshTokenDO.getUserId(), refreshTokenDO.getUserType(), accessTokenDO.getTenantIds(),
                true);
    }

    @Override
    public TokenAccessDO getAccessToken(String accessToken) {
        // 优先从 Redis 中获取
        TokenAccessDO accessTokenDO = accessTokenRedisDAO.get(accessToken);
        if (accessTokenDO != null) {
            return accessTokenDO;
        }

        // 获取不到，从 MySQL 中获取
        accessTokenDO = tokenAccessTokenMapper.selectByAccessToken(accessToken);
        // 如果在 MySQL 存在，则往 Redis 中写入
        if (accessTokenDO != null && !DateUtils.isExpired(accessTokenDO.getExpiresTime())) {
            accessTokenRedisDAO.set(accessTokenDO);
        }
        return accessTokenDO;
    }

    @Override
    public TokenAccessDO checkAccessToken(String accessToken) {
        TokenAccessDO accessTokenDO = getAccessToken(accessToken);
        if (accessTokenDO == null) {
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "访问令牌不存在");
        }
        if (DateUtils.isExpired(accessTokenDO.getExpiresTime())) {
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "访问令牌已过期");
        }
        return accessTokenDO;
    }

    @Override
    public TokenAccessDO removeAccessToken(String accessToken) {
        // 删除访问令牌
        TokenAccessDO accessTokenDO = tokenAccessTokenMapper.selectByAccessToken(accessToken);
        if (accessTokenDO == null) {
            return null;
        }
        tokenAccessTokenMapper.deleteById(accessTokenDO.getId());
        accessTokenRedisDAO.delete(accessToken);
        // 删除刷新令牌
        tokenRefreshTokenMapper.deleteByRefreshToken(accessTokenDO.getRefreshToken());
        return accessTokenDO;
    }

    @Override
    public PageResult<TokenAccessDO> getAccessTokenPage(AccessTokenPageReqVO reqVO) {
        return tokenAccessTokenMapper.selectPage(reqVO);
    }

    private TokenRefreshDO createRefreshToken(Long userId, Integer userType) {
        TokenRefreshDO refreshToken = new TokenRefreshDO().setRefreshToken(generateRefreshToken())
                .setUserId(userId).setUserType(userType)
                .setExpiresTime(LocalDateTime.now().plusSeconds(360000)); // todo 配置过期时间
        tokenRefreshTokenMapper.insert(refreshToken);
        return refreshToken;
    }

    private static String generateAccessToken() {
        return IdUtil.fastSimpleUUID();
    }

    private static String generateRefreshToken() {
        return IdUtil.fastSimpleUUID();
    }


    /**
     * 获得自身的代理对象，解决 AOP 生效问题
     *
     * @return 自己
     */
    private TokenServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }
}
