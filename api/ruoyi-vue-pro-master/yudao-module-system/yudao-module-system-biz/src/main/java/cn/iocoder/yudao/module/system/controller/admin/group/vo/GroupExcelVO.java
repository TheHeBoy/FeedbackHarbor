package cn.iocoder.yudao.module.system.controller.admin.group.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 用户组 Excel VO
 *
 *  hehong
 */
@Data
public class GroupExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("名字")
    private String name;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("状态")
    private Byte status;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
