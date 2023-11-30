package cn.hh.harbor.module.system.enums.mail;

import cn.hh.harbor.framework.common.core.IntArrayValuable;
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户短信验证码发送场景的枚举
 */
@Getter
@AllArgsConstructor
public enum MailSceneEnum implements IntArrayValuable {

    REGISTER(1, MailTemplateEnum.REGISTER_CAPTCHA, "注册"),
    RESET_PASSWD(2, MailTemplateEnum.RESET_PASSWD_CAPTCHA, "重置密码"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(MailSceneEnum::getScene).toArray();

    /**
     * 验证场景的编号
     */
    private final Integer scene;
    /**
     * 模版编码
     */
    private final String templateCode;
    /**
     * 描述
     */
    private final String description;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static MailSceneEnum getCodeByScene(Integer scene) {
        return ArrayUtil.firstMatch(sceneEnum -> sceneEnum.getScene().equals(scene),
                values());
    }

}
