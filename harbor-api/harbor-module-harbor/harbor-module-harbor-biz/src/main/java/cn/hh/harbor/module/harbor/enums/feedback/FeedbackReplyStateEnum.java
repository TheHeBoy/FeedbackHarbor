package cn.hh.harbor.module.harbor.enums.feedback;

import cn.hh.harbor.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static cn.hutool.core.util.ArrayUtil.firstMatch;

/**
 * 反馈回复状态
 * @author hehong
 * @date 2023-09-25
 */
@AllArgsConstructor
@Getter
public enum FeedbackReplyStateEnum implements IntArrayValuable {

    NO_REPLY(0, "待回复"),
    REPLIED(1, "已回复"),
    ;

    private final Integer code;

    private final String name;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(FeedbackReplyStateEnum::getCode).toArray();

    public static FeedbackReplyStateEnum valueOf(Integer code) {
        return firstMatch(sceneEnum -> sceneEnum.getCode().equals(code), values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
