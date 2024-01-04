package cn.hh.harbor.module.harbor.enums.common;

import cn.hh.harbor.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static cn.hutool.core.util.ArrayUtil.firstMatch;

/**
 * 业务类型 - 点赞功能，举报功能
 * @author hehong
 * @date 2023-09-25
 */
@AllArgsConstructor
@Getter
public enum BusTypeEnum implements IntArrayValuable {

    FEEDBACK(0, "反馈"),
    COMMENT(1, "评论"),
    ;

    private final Integer code;

    private final String name;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BusTypeEnum::getCode).toArray();

    public static BusTypeEnum valueOf(Integer code) {
        return firstMatch(sceneEnum -> sceneEnum.getCode().equals(code), values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
