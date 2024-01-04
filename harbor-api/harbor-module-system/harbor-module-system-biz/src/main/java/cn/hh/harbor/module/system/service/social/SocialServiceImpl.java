package cn.hh.harbor.module.system.service.social;

import cn.hh.harbor.module.system.config.SocialProperties;
import cn.hh.harbor.module.system.enums.social.SocialTypeEnum;
import com.xingyuv.http.config.HttpConfig;
import com.xingyuv.jushauth.config.AuthConfig;
import com.xingyuv.jushauth.request.AuthGiteeRequest;
import com.xingyuv.jushauth.request.AuthGithubRequest;
import com.xingyuv.jushauth.request.AuthRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.annotation.Resource;
import java.net.URL;
import java.util.Map;

import static cn.hh.harbor.module.system.enums.social.SocialTypeEnum.GITEE;
import static cn.hh.harbor.module.system.enums.social.SocialTypeEnum.GITHUB;

@Service
public class SocialServiceImpl implements SocialService {

    @Resource
    private SocialProperties socialProperties;

    @Override
    public AuthRequest getAuthRequest(SocialTypeEnum socialTypeEnum, String redirectUri) {
        Map<String, Map<String, SocialProperties.SocialApp>> typeMap = socialProperties.getType();
        SocialProperties.Proxy proxy = socialProperties.getProxy();
        AuthRequest authRequest = null;
        String clientSideType = getClientSide(redirectUri);
        switch (socialTypeEnum) {
            case GITEE:
                SocialProperties.SocialApp giteeClient = typeMap.get(GITEE.getSource()).get(clientSideType);
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(giteeClient.getClientId())
                        .clientSecret(giteeClient.getClientSecret())
                        .redirectUri(redirectUri)
                        .build());
                break;
            case GITHUB:
                SocialProperties.SocialApp gitHubClient = typeMap.get(GITHUB.getSource()).get(clientSideType);
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId(gitHubClient.getClientId())
                        .clientSecret(gitHubClient.getClientSecret())
                        .redirectUri(redirectUri)
                        // 针对国外平台配置代理
                        .httpConfig(HttpConfig.builder()
                                .timeout(proxy.getTimeout())
                                // host 和 port 请修改为开发环境的参数
                                .proxy(new Proxy(Proxy.Type.valueOf(proxy.getType()),
                                        new InetSocketAddress(proxy.getHostname(), proxy.getPort())))
                                .build())
                        .build());
                break;
        }
        return authRequest;
    }

    /**
     * 判断 url 是 app 端还是 admin 端
     *
     * @param url url
     * @return {@link Integer}
     */
    @SneakyThrows
    private static String getClientSide(String url) {
        String path = new URL(url).getPath();
        if (path.startsWith("/dashboard")) {
            return "Admin";
        } else if (path.startsWith("/product")) {
            return "App";
        } else {
            return null;
        }
    }
}
