package cn.iocoder.yudao.module.system.dal.redis;

import cn.iocoder.yudao.module.system.dal.dataobject.token.TokenAccessDO;

/**
 * System Redis Key 枚举类
 *
 *
 */
public interface RedisKeyConstants {
    /**
     * 角色的缓存
     * <p>
     * KEY 格式：role:{id}
     * VALUE 数据类型：String 角色信息
     */
    String ROLE = "role";

    /**
     * 用户拥有的角色编号的缓存
     * <p>
     * KEY 格式：user_role_ids:{userId}
     * VALUE 数据类型：String 角色编号集合
     */
    String USER_ROLE_ID_LIST = "user_role_ids";

    /**
     * 拥有指定菜单的角色编号的缓存
     * <p>
     * KEY 格式：user_role_ids:{menuId}
     * VALUE 数据类型：String 角色编号集合
     */
    String MENU_ROLE_ID_LIST = "menu_role_ids";

    /**
     * 拥有权限对应的菜单编号数组的缓存
     * <p>
     * KEY 格式：permission_menu_ids:{permission}
     * VALUE 数据类型：String 菜单编号数组
     */
    String PERMISSION_MENU_ID_LIST = "permission_menu_ids";


    /**
     * 访问令牌的缓存
     * <p>
     * KEY 格式：access_token:{token}
     * VALUE 数据类型：String 访问令牌信息 {@link TokenAccessDO}
     * <p>
     * 由于动态过期时间，使用 RedisTemplate 操作
     */
    String ACCESS_TOKEN = "access_token:%s";

    /**
     * 邮件账号的缓存
     * <p>
     * KEY 格式：sms_template:{id}
     * VALUE 数据格式：String 账号信息
     */
    String MAIL_ACCOUNT = "mail_account";

    /**
     * 邮件模版的缓存
     * <p>
     * KEY 格式：mail_template:{code}
     * VALUE 数据格式：String 模版信息
     */
    String MAIL_TEMPLATE = "mail_template";

    /**
     * 短信模版的缓存
     * <p>
     * KEY 格式：sms_template:{id}
     * VALUE 数据格式：String 模版信息
     */
    String SMS_TEMPLATE = "sms_template";
}
