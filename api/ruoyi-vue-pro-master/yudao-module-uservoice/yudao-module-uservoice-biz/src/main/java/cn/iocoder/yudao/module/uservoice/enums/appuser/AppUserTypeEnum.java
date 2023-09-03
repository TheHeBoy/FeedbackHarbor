package cn.iocoder.yudao.module.uservoice.enums.appuser;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static cn.hutool.core.util.ArrayUtil.firstMatch;


@AllArgsConstructor
@Getter
public enum AppUserTypeEnum {

    BUILTIN(0, "内置用户"),
    THIRD_PARTY(1, "第三方用户"),
    LOGIN_STATE(2, "登录态用户");

    private final Integer code;

    private final String name;

    public static AppUserTypeEnum valueOf(Integer code) {
        return firstMatch(sceneEnum -> sceneEnum.getCode().equals(code), values());
    }

}
