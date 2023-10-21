package cn.iocoder.yudao.module.harbor.enums.feedback;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static cn.hutool.core.util.ArrayUtil.firstMatch;

/**
 * 反馈状态
 * @author hehong
 * @date 2023-09-25
 */
@AllArgsConstructor
@Getter
public enum FeedbackStateEnum implements IntArrayValuable {

    REPLIED(0, "已回复"),
    NO_REPLY(1, "未回复"),
    ;

    private final Integer code;

    private final String name;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(FeedbackStateEnum::getCode).toArray();

    public static FeedbackStateEnum valueOf(Integer code) {
        return firstMatch(sceneEnum -> sceneEnum.getCode().equals(code), values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
