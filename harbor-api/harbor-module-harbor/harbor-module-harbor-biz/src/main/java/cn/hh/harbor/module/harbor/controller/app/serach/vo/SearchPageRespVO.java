package cn.hh.harbor.module.harbor.controller.app.serach.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Data
public class SearchPageRespVO{

    @Schema(description = "关联id")
    private Long rid;

    @Schema(description = "高亮内容")
    private List<String> highContent;
}
