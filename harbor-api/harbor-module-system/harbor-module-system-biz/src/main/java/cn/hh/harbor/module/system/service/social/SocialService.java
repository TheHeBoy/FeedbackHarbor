package cn.hh.harbor.module.system.service.social;

import cn.hh.harbor.module.system.enums.social.SocialTypeEnum;
import com.xingyuv.jushauth.request.AuthRequest;

public interface SocialService {
    AuthRequest getAuthRequest(SocialTypeEnum socialTypeEnum,String redirectUri);
}
