package cn.hh.harbor.module.harbor.controller.app.like.vo;

import cn.hh.harbor.framework.common.validation.InEnum;
import cn.hh.harbor.module.harbor.enums.common.BusTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "App - 用户点赞 Request VO")
@Data
@ToString(callSuper = true)
public class AppLikeReqVO {

    @Schema(description = "关联id")
    private Long rid;

    @Schema(description = "业务类型")
    @InEnum(BusTypeEnum.class)
    private int busType;
}
