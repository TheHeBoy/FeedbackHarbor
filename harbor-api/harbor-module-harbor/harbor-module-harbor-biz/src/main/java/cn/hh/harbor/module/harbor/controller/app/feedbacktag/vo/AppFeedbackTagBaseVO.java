package cn.hh.harbor.module.harbor.controller.app.feedbacktag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 反馈标签 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AppFeedbackTagBaseVO {

    @Schema(description = "标签名中文")
    private String nameCh;

    @Schema(description = "标签名英语")
    private String nameEn;

    @Schema(description = "标签顺序")
    private Integer sort;

    @Schema(description = "标签颜色")
    private String color;

}
