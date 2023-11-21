package cn.hh.harbor.module.harbor.controller.app.like.vo;

import cn.hh.harbor.framework.common.validation.InEnum;
import cn.hh.harbor.module.harbor.enums.like.LikeBusTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "App - 用户点赞 Request VO")
@Data
@ToString(callSuper = true)
public class AppLikeListReqVO {

    @Schema(description = "业务类型")
    @InEnum(LikeBusTypeEnum.class)
    private int busType;
}
