package cn.hh.harbor.module.system.controller.admin.invite;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理后台 - 团队邮件邀请")
@RestController
@RequestMapping("/system/invite/email")
@Validated
public class InviteEmailController {

}