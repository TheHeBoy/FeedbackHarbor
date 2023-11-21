package cn.hh.harbor.module.harbor.enums.feedback;

import cn.hh.harbor.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static cn.hutool.core.util.ArrayUtil.firstMatch;

/**
 * 排序
 * @author hehong
 * @date 2023-09-25
 */
@AllArgsConstructor
@Getter
public enum FeedbackOrderEnum implements IntArrayValuable {

    RECOMMEND(0, "推荐"),
    NEWEST(1, "最新"),
    ;

    private final Integer code;

    private final String name;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(FeedbackOrderEnum::getCode).toArray();

    public static FeedbackOrderEnum valueOf(Integer code) {
        return firstMatch(sceneEnum -> sceneEnum.getCode().equals(code), values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
