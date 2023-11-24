package cn.hh.harbor.module.system.enums;

import cn.hh.harbor.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 * <p>
 * system 系统，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== AUTH 模块 1002000000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1002000000, "登录失败，账号密码不正确");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1002000001, "登录失败，账号被禁用");
    ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1002000004, "验证码不正确，原因：{}");
    ErrorCode AUTH_THIRD_LOGIN_NOT_BIND = new ErrorCode(1002000005, "未绑定账号，需要进行绑定");
    ErrorCode AUTH_TOKEN_EXPIRED = new ErrorCode(1002000006, "Token 已经过期");
    ErrorCode AUTH_MOBILE_NOT_EXISTS = new ErrorCode(1002000007, "手机号不存在");

    // ========== 菜单模块 1002001000 ==========
    ErrorCode MENU_NAME_DUPLICATE = new ErrorCode(1002001000, "已经存在该名字的菜单");
    ErrorCode MENU_PARENT_NOT_EXISTS = new ErrorCode(1002001001, "父菜单不存在");
    ErrorCode MENU_PARENT_ERROR = new ErrorCode(1002001002, "不能设置自己为父菜单");
    ErrorCode MENU_NOT_EXISTS = new ErrorCode(1002001003, "菜单不存在");
    ErrorCode MENU_EXISTS_CHILDREN = new ErrorCode(1002001004, "存在子菜单，无法删除");
    ErrorCode MENU_PARENT_NOT_DIR_OR_MENU = new ErrorCode(1002001005, "父菜单的类型必须是目录或者菜单");

    // ========== 角色模块 1002002000 ==========
    ErrorCode ROLE_NOT_EXISTS = new ErrorCode(1002002000, "角色不存在");
    ErrorCode ROLE_NAME_DUPLICATE = new ErrorCode(1002002001, "已经存在名为【{}】的角色");
    ErrorCode ROLE_CODE_DUPLICATE = new ErrorCode(1002002002, "已经存在编码为【{}】的角色");
    ErrorCode ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE = new ErrorCode(1002002003, "不能操作类型为系统内置的角色");
    ErrorCode ROLE_IS_DISABLE = new ErrorCode(1002002004, "名字为【{}】的角色已被禁用");
    ErrorCode ROLE_ADMIN_CODE_ERROR = new ErrorCode(1002002005, "编码【{}】不能使用");

    // ========== 用户模块 1002003000 ==========
    ErrorCode USER_USERNAME_EXISTS = new ErrorCode(1002003000, "用户账号已经存在");
    ErrorCode USER_MOBILE_EXISTS = new ErrorCode(1002003001, "手机号已经存在");
    ErrorCode USER_EMAIL_EXISTS = new ErrorCode(1002003002, "邮箱已经存在");
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1002003003, "用户不存在");
    ErrorCode USER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1002003004, "导入用户数据不能为空！");
    ErrorCode USER_PASSWORD_FAILED = new ErrorCode(1002003005, "用户密码校验失败");
    ErrorCode USER_IS_DISABLE = new ErrorCode(1002003006, "名字为【{}】的用户已被禁用");
    ErrorCode USER_COUNT_MAX = new ErrorCode(1002003008, "创建用户失败，原因：超过租户最大租户配额({})！");


    // ========== 字典类型 1002006000 ==========
    ErrorCode DICT_TYPE_NOT_EXISTS = new ErrorCode(1002006001, "当前字典类型不存在");
    ErrorCode DICT_TYPE_NOT_ENABLE = new ErrorCode(1002006002, "字典类型不处于开启状态，不允许选择");
    ErrorCode DICT_TYPE_NAME_DUPLICATE = new ErrorCode(1002006003, "已经存在该名字的字典类型");
    ErrorCode DICT_TYPE_TYPE_DUPLICATE = new ErrorCode(1002006004, "已经存在该类型的字典类型");
    ErrorCode DICT_TYPE_HAS_CHILDREN = new ErrorCode(1002006005, "无法删除，该字典类型还有字典数据");

    // ========== 字典数据 1002007000 ==========
    ErrorCode DICT_DATA_NOT_EXISTS = new ErrorCode(1002007001, "当前字典数据不存在");
    ErrorCode DICT_DATA_NOT_ENABLE = new ErrorCode(1002007002, "字典数据({})不处于开启状态，不允许选择");
    ErrorCode DICT_DATA_VALUE_DUPLICATE = new ErrorCode(1002007003, "已经存在该值的字典数据");

    // ========== 通知公告 1002008000 ==========
    ErrorCode NOTICE_NOT_FOUND = new ErrorCode(1002008001, "当前通知公告不存在");

    // ========== 短信渠道 1002011000 ==========
    ErrorCode SMS_CHANNEL_NOT_EXISTS = new ErrorCode(1002011000, "短信渠道不存在");
    ErrorCode SMS_CHANNEL_DISABLE = new ErrorCode(1002011001, "短信渠道不处于开启状态，不允许选择");
    ErrorCode SMS_CHANNEL_HAS_CHILDREN = new ErrorCode(1002011002, "无法删除，该短信渠道还有短信模板");

    // ========== 短信模板 1002012000 ==========
    ErrorCode SMS_TEMPLATE_NOT_EXISTS = new ErrorCode(1002012000, "短信模板不存在");
    ErrorCode SMS_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1002012001, "已经存在编码为【{}】的短信模板");

    // ========== 短信发送 1002013000 ==========
    ErrorCode SMS_SEND_MOBILE_NOT_EXISTS = new ErrorCode(1002013000, "手机号不存在");
    ErrorCode SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS = new ErrorCode(1002013001, "模板参数({})缺失");
    ErrorCode SMS_SEND_TEMPLATE_NOT_EXISTS = new ErrorCode(1002013002, "短信模板不存在");

    // ========== 短信验证码 1002014000 ==========
    ErrorCode SMS_CODE_NOT_FOUND = new ErrorCode(1002014000, "验证码不存在");
    ErrorCode SMS_CODE_EXPIRED = new ErrorCode(1002014001, "验证码已过期");
    ErrorCode SMS_CODE_USED = new ErrorCode(1002014002, "验证码已使用");
    ErrorCode SMS_CODE_NOT_CORRECT = new ErrorCode(1002014003, "验证码不正确");
    ErrorCode SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY = new ErrorCode(1002014004, "超过每日短信发送数量");
    ErrorCode SMS_CODE_SEND_TOO_FAST = new ErrorCode(1002014005, "短信发送过于频率");
    ErrorCode SMS_CODE_IS_EXISTS = new ErrorCode(1002014006, "手机号已被使用");
    ErrorCode SMS_CODE_IS_UNUSED = new ErrorCode(1002014007, "验证码未被使用");

    // ========== 租户信息 1002015000 ==========
    ErrorCode TENANT_NOT_EXISTS = new ErrorCode(1002015000, "租户不存在");
    ErrorCode TENANT_DISABLE = new ErrorCode(1002015001, "名字为【{}】的租户已被禁用");
    ErrorCode TENANT_EXPIRE = new ErrorCode(1002015002, "名字为【{}】的租户已过期");
    ErrorCode TENANT_ROUTER_URI_DUPLICATE = new ErrorCode(1002015004, "社区租户路由为【{}】的社区租户已存在");
    ErrorCode TENANT_SYSTEM_DELETE = new ErrorCode(1002015005, "内置租户不能删除");
    ErrorCode TENANT_SYSTEM_UPDATE = new ErrorCode(1002015006, "内置租户不能修改");

    // ========== 租户套餐 1002016000 ==========
    ErrorCode TENANT_PACKAGE_NOT_EXISTS = new ErrorCode(1002016000, "租户套餐不存在");
    ErrorCode TENANT_PACKAGE_USED = new ErrorCode(1002016001, "租户正在使用该套餐，请给租户重新设置套餐后再尝试删除");
    ErrorCode TENANT_PACKAGE_DEFAULT_DELETE = new ErrorCode(1002016002, "默认套餐不能删除");

    // ========== 社交用户 1002018000 ==========
    ErrorCode SOCIAL_USER_AUTH_FAILURE = new ErrorCode(1002018000, "社交授权失败，原因是：{}");

    // ========== 系统敏感词 1002019000 =========
    ErrorCode SENSITIVE_WORD_NOT_EXISTS = new ErrorCode(1002019000, "系统敏感词在所有标签中都不存在");
    ErrorCode SENSITIVE_WORD_EXISTS = new ErrorCode(1002019001, "系统敏感词已在标签中存在");

    // ========== 邮箱账号 1002023000 ==========
    ErrorCode MAIL_ACCOUNT_NOT_EXISTS = new ErrorCode(1002023000, "邮箱账号不存在");
    ErrorCode MAIL_ACCOUNT_RELATE_TEMPLATE_EXISTS = new ErrorCode(1002023001, "无法删除，该邮箱账号还有邮件模板");

    // ========== 邮件模版 1002024000 ==========
    ErrorCode MAIL_TEMPLATE_NOT_EXISTS = new ErrorCode(1002024000, "邮件模版不存在");
    ErrorCode MAIL_TEMPLATE_CODE_EXISTS = new ErrorCode(1002024001, "邮件模版 code({}) 已存在");

    // ========== 邮件发送 1002025000 ==========
    ErrorCode MAIL_SEND_TEMPLATE_PARAM_MISS = new ErrorCode(1002025000, "模板参数({})缺失");
    ErrorCode MAIL_SEND_MAIL_NOT_EXISTS = new ErrorCode(1002025001, "邮箱不存在");

    // ========== 团队邀请 1002026000 ==========
    ErrorCode INVITE_NO_EXISTS = new ErrorCode(1002026000, "邀请不存在");
}
