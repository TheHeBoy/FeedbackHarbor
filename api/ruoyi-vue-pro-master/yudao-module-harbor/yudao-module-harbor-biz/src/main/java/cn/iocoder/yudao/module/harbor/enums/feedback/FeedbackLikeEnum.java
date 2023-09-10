package cn.iocoder.yudao.module.harbor.enums.feedback;

import cn.iocoder.yudao.module.harbor.enums.appuser.AppUserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static cn.hutool.core.util.ArrayUtil.firstMatch;

@AllArgsConstructor
@Getter
public enum FeedbackLikeEnum {

    LIKED(0, "点赞"),
    LIKED_CANCEL(1, "取消点赞"),
    ;

    private final Integer code;

    private final String name;

    public static FeedbackLikeEnum valueOf(Integer code) {
        return firstMatch(sceneEnum -> sceneEnum.getCode().equals(code), values());
    }
}
