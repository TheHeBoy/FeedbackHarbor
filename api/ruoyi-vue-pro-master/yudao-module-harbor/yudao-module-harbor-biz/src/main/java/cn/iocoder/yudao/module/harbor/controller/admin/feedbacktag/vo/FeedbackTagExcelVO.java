package cn.iocoder.yudao.module.harbor.controller.admin.feedbacktag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 反馈标签 Excel VO
 *
 *  hehong
 */
@Data
public class FeedbackTagExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("标签名中文")
    private String nameCh;

    @ExcelProperty("标签名英语")
    private String nameEn;

    @ExcelProperty("标签顺序")
    private Long sort;

    @ExcelProperty("标签颜色")
    private String color;

}
