package cn.hh.harbor.module.harbor.dal.dataobject.feedback;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;

/**
 * 用户反馈
 *
 * @author hehong
 * @date 2023-12-23
 */
@Document(indexName = "feedback")
@Data
public class FeedbackES {

    /**
     * 文档主键 唯一标识
     */
    @Id
    @Field(store = true, index = false, type = FieldType.Long)
    private Long id;

    /**
     * 反馈内容
     */
    @Field(analyzer = "ik_smart", store = true, searchAnalyzer = "ik_max_word", type = FieldType.Text)
    private String content;

    /**
     * 多租户编号
     */
    @Field(store = true, type = FieldType.Long)
    private Long tenantId;
}
