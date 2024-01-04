package cn.hh.harbor.module.system.service.token;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hh.harbor.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.common.util.date.DateUtils;
import cn.hh.harbor.module.system.controller.admin.token.vo.AccessTokenPageReqVO;
import cn.hh.harbor.module.system.dal.dataobject.token.TokenAccessDO;
import cn.hh.harbor.module.system.dal.mysql.token.TokenAccessTokenMapper;
import cn.hh.harbor.module.system.dal.redis.token.AccessTokenRedisDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static cn.hh.harbor.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * Token Service 实现类
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private TokenAccessTokenMapper tokenAccessTokenMapper;
    @Resource
    private AccessTokenRedisDAO accessTokenRedisDAO;

    @Value("${harbor.token-access-time}")
    private Duration tokenAccessTime;

    @Override
    @Transactional
    public TokenAccessDO createAccessToken(Long userId, List<Long> tenantIds) {

        // 创建访问令牌
        TokenAccessDO accessTokenDO = new TokenAccessDO().setAccessToken(generateAccessToken())
                .setUserId(userId)
                .setTenantIds(tenantIds)
                .setExpiresTime(LocalDateTime.now().plus(tokenAccessTime));
        tokenAccessTokenMapper.insert(accessTokenDO);
        // 记录到 Redis 中
        accessTokenRedisDAO.set(accessTokenDO);
        return accessTokenDO;
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
        return accessTokenDO;
    }

    @Override
    public PageResult<TokenAccessDO> getAccessTokenPage(AccessTokenPageReqVO reqVO) {
        return tokenAccessTokenMapper.selectPage(reqVO);
    }

    @Transactional
    @Override
    public void addTenantIdByAccessToken(Long tenantId, String accessToken) {
        TokenAccessDO tokenAccessDO = getSelf().getAccessToken(accessToken);
        tokenAccessDO.getTenantIds().add(tenantId);
        tokenAccessTokenMapper.updateById(tokenAccessDO);
        accessTokenRedisDAO.update(accessToken, tokenAccessDO);
    }

    private static String generateAccessToken() {
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
