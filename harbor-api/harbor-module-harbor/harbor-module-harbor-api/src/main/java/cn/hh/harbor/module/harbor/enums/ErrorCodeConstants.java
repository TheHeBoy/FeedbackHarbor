package cn.hh.harbor.module.harbor.enums;

import cn.hh.harbor.framework.common.exception.ErrorCode;

/**
 * Harbor 错误码枚举类
 * harbor 系统，使用 1-015-000-000 ~ 1-016-000-000 段
 */
public interface ErrorCodeConstants {
    // ========== Feedback 模块 1015002000 ==========
    ErrorCode FEEDBACK_NOT_EXISTS = new ErrorCode(1015002001, "用户反馈不存在");

    // ========== Comment 模块 1015003000 ==========
    ErrorCode COMMENT_NOT_EXISTS = new ErrorCode(1015003001, "评论不存在");

    // ========== File 模块 1015004000 ==========
    ErrorCode File_NOT_NULL = new ErrorCode(1015004001, "文件不能为空");

    // ========== 反馈标签 1015005000 ==========
    ErrorCode FEEDBACK_TAG_NOT_EXISTS = new ErrorCode(1015005001, "反馈标签不存在");
}
