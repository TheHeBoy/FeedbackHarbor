package cn.iocoder.yudao.module.harbor.controller.app.like.vo;

import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.harbor.enums.like.LikeBusTypeEnum;
import cn.iocoder.yudao.module.system.enums.sms.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(description = "App - 用户点赞 Request VO")
@Data
@ToString(callSuper = true)
public class AppLikeReqVO {

    @Schema(description = "关联id")
    private Long rid;

    @Schema(description = "业务类型")
    @InEnum(LikeBusTypeEnum.class)
    private int busType;
}
