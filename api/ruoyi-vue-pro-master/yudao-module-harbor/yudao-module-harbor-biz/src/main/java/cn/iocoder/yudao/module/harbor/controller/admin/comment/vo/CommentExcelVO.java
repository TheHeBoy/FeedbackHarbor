package cn.iocoder.yudao.module.harbor.controller.admin.comment.vo;

import lombok.*;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 评论 Excel VO
 *
 *  hehong
 */
@Data
public class CommentExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("父节点")
    private Long parentId;

    @ExcelProperty("用户id")
    private Long uid;

    @ExcelProperty("反馈id")
    private Long feedbackId;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("点赞数")
    private Long likes;

    @ExcelProperty("用户类型;")
    private Integer userType;

    @ExcelProperty("头像;")
    private String avatar;

    @ExcelProperty("用户昵称;")
    private String nickname;

}
