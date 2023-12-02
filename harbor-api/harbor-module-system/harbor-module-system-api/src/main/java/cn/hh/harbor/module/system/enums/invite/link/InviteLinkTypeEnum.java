package cn.hh.harbor.module.system.enums.invite.link;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 链接类型
 *
 * @author hehong
 * @date 2023-12-01
 */
@AllArgsConstructor
@Getter
public enum InviteLinkTypeEnum {
    /**
     * 每个租户只会有一个邀请链接
     */
    LINK(1,"邀请链接"),
    MAIL(2,"邮箱邀请"),
    ;

    private final Integer code;
    private final String name;
}
