package cn.hh.harbor.module.system.controller.admin.user.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(description = "管理后台 - 用户分页时的信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageItemRespVO extends UserRespVO {

}
