package cn.hh.harbor.module.system.controller.app.auth.vo;

import cn.hh.harbor.framework.common.validation.InEnum;
import cn.hh.harbor.module.system.controller.admin.auth.vo.AuthSocialLoginReqVO;
import cn.hh.harbor.module.system.enums.social.SocialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "APP - 社交登录 Request VO")
@Data
public class AppAuthSocialLoginReqVO extends AuthSocialLoginReqVO {
}
