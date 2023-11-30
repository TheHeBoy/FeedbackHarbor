package cn.hh.harbor.module.system.enums.invite.user;

import cn.hh.harbor.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 邀请状态
 *
 * @author hehong
 * @date 2023-11-24
 */
@AllArgsConstructor
@Getter
public enum InviteStatusEnum implements IntArrayValuable {

    // 用户邀请
    ACCEPT(1, "同意"),
    REFUSE(2, "拒绝"),
    NO_REPLY(3, "待回复"),
    ;

    private final Integer code;
    private final String name;
    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(InviteStatusEnum::getCode).toArray();


    @Override
    public int[] array() {
        return ARRAYS;
    }
}
