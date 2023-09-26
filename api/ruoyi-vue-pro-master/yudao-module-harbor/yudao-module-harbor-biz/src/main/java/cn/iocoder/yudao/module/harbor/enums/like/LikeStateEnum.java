package cn.iocoder.yudao.module.harbor.enums.like;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static cn.hutool.core.util.ArrayUtil.firstMatch;

@AllArgsConstructor
@Getter
public enum LikeStateEnum {

    LIKED(0, "点赞"),
    LIKED_CANCEL(1, "无点赞"),
    ;

    private final Integer code;

    private final String name;

    public static LikeStateEnum valueOf(Integer code) {
        return firstMatch(sceneEnum -> sceneEnum.getCode().equals(code), values());
    }
}
