package cn.hh.harbor.module.harbor.controller.admin.feedback.vo;

import lombok.*;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 用户反馈 Excel VO
 *
 */
@Data
public class FeedbackExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("用户id")
    private Long uid;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("点赞数")
    private Long likes;

    @ExcelProperty("反馈标签id")
    private Integer feedbackTagId;

}
