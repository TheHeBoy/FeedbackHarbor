package cn.iocoder.yudao.module.harbor.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * uservoice 系统，使用 1-015-000-000 ~ 1-016-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 用户模块 1015000000 ==========
    ErrorCode APP_USER_NOT_EXISTS = new ErrorCode(1015000001, "App用户不存在");

    // ========== AUTH 模块 1015001000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1015001001, "登录失败，账号密码不正确");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1015001002, "登录失败，账号被禁用");

    // ========== Feedback 模块 1015002000 ==========
    ErrorCode FEEDBACK_NOT_EXISTS = new ErrorCode(1015002001, "用户反馈不存在");

    // ========== 评论 模块 1015003000 ==========
    ErrorCode COMMENT_NOT_EXISTS = new ErrorCode(1015003001, "评论不存在");
}
