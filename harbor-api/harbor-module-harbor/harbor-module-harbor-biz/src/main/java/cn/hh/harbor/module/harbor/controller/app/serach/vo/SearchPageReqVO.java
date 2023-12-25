package cn.hh.harbor.module.harbor.controller.app.serach.vo;

import cn.hh.harbor.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchPageReqVO extends PageParam {

    @Schema(description = "搜索词")
    @NotBlank(message = "搜索词不能为空")
    private String searchWords;
}
