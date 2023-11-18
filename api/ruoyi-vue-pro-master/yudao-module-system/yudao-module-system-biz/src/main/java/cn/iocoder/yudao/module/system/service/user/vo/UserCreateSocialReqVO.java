package cn.iocoder.yudao.module.system.service.user.vo;

import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.module.system.enums.social.SocialTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateSocialReqVO {
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 社交平台的类型
     * 枚举 {@link SocialTypeEnum}
     */
    private Integer socialType;
    /**
     * 社交 openid
     */
    private String socialOpenId;
}
