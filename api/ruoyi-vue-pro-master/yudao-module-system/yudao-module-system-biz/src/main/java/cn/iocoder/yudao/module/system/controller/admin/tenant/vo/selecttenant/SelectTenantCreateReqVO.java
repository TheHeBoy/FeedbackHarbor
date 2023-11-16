package cn.iocoder.yudao.module.system.controller.admin.tenant.vo.selecttenant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 租户创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SelectTenantCreateReqVO extends SelectTenantBaseVO{
}
