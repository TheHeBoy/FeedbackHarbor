package cn.hh.harbor.module.system.controller.admin.user.vo.user;

import cn.hh.harbor.framework.excel.core.annotations.DictFormat;
import cn.hh.harbor.framework.excel.core.convert.DictConvert;
import cn.hh.harbor.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户 Excel 导出 VO
 */
@Data
public class UserExcelVO {

    @ExcelProperty("用户编号")
    private Long id;

    @ExcelProperty("用户名称")
    private String username;

    @ExcelProperty("用户昵称")
    private String nickname;

    @ExcelProperty("用户邮箱")
    private String email;

    @ExcelProperty("手机号码")
    private String mobile;

    @ExcelProperty(value = "帐号状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @ExcelProperty("最后登录IP")
    private String loginIp;

    @ExcelProperty("最后登录时间")
    private LocalDateTime loginDate;

}
