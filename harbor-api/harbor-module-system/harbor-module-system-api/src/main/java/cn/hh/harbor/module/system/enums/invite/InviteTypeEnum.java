package cn.hh.harbor.module.system.enums.invite;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 邀请类型
 *
 * @author hehong
 * @date 2023-11-24
 */
@AllArgsConstructor
@Getter
public enum InviteTypeEnum {

    EMAIL(1, "邮件"),
    USER(2, "用户"),
    LINK(3, "链接"),
    ;

    private final Integer code;
    private final String name;


}
