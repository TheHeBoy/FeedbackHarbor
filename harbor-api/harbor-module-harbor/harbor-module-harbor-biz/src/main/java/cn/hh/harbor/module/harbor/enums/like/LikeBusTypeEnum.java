package cn.hh.harbor.module.harbor.enums.like;

import cn.hh.harbor.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static cn.hutool.core.util.ArrayUtil.firstMatch;

/**
 * 使用点赞功能的业务类型
 * @author hehong
 * @date 2023-09-25
 */
@AllArgsConstructor
@Getter
public enum LikeBusTypeEnum implements IntArrayValuable {

    FEEDBACK(0, "反馈"),
    COMMENT(1, "评论"),
    ;

    private final Integer code;

    private final String name;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(LikeBusTypeEnum::getCode).toArray();

    public static LikeBusTypeEnum valueOf(Integer code) {
        return firstMatch(sceneEnum -> sceneEnum.getCode().equals(code), values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
