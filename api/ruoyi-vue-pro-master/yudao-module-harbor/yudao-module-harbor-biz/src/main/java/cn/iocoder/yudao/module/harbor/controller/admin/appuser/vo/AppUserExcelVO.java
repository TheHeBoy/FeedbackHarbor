package cn.iocoder.yudao.module.harbor.controller.admin.appuser.vo;

import lombok.*;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * App用户 Excel VO
 *
 *  hehong
 */
@Data
public class AppUserExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty(value = "用户类型", converter = DictConvert.class)
    @DictFormat("uservoice_app_user_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte userType;

    @ExcelProperty("用户账号")
    private String username;

    @ExcelProperty("密码")
    private String password;

    @ExcelProperty("登录态用户id")
    private String userOpenId;

    @ExcelProperty("头像")
    private String avatar;

    @ExcelProperty("用户昵称")
    private String nickname;

    @ExcelProperty(value = "用户状态", converter = DictConvert.class)
    @DictFormat("uservoice_app_user_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

}
