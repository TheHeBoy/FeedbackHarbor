CREATE TABLE harbor_app_user
(
    `id`           bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_time`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `creator`      VARCHAR(64) COMMENT '创建者',
    `updater`      VARCHAR(64) COMMENT '更新者',
    `deleted`      tinyint  NOT NULL DEFAULT 0 COMMENT '是否删除',
    `user_type`    tinyint  NOT NULL COMMENT '用户类型',
    `username`     VARCHAR(255) COMMENT '用户账号',
    `password`     VARCHAR(100) COMMENT '密码',
    `user_open_id` VARCHAR(255) COMMENT '登录态用户id',
    `avatar`       VARCHAR(512) COMMENT '头像',
    `nickname`     VARCHAR(30) COMMENT '用户昵称',
    `status`       tinyint           DEFAULT 0 COMMENT '用户状态',
    PRIMARY KEY (id)
) COMMENT = 'App用户表';

CREATE TABLE harbor_comment
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `creator`     VARCHAR(64) COMMENT '创建者',
    `updater`     VARCHAR(64) COMMENT '更新者',
    `deleted`     INT      NOT NULL DEFAULT 0 COMMENT '是否删除',
    `parent_id`   bigint COMMENT '父节点',
    `uid`         bigint COMMENT '用户id',
    `feedback_id` bigint COMMENT '反馈id',
    `content`     VARCHAR(500) COMMENT '内容',
    `likes`       bigint            DEFAULT 0 COMMENT '点赞数',
    `user_type`   tinyint  NOT NULL COMMENT '用户类型',
    `avatar`      VARCHAR(512) COMMENT '头像',
    `nickname`    VARCHAR(30) COMMENT '用户昵称',
    PRIMARY KEY (id)
) COMMENT = '评论表';

CREATE TABLE harbor_comment_like
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `comment_id`  bigint COMMENT '评论id',
    `uid`         bigint COMMENT '用户id',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `creator`     VARCHAR(64) COMMENT '创建者',
    `updater`     VARCHAR(64) COMMENT '更新者',
    `liked`       tinyint           DEFAULT 0 COMMENT '是否点赞',
    `deleted`     tinyint  NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id)
) COMMENT = '评论点赞关联表';

CREATE TABLE harbor_feedback
(
    `id`            bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_time`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `creator`       VARCHAR(64) COMMENT '创建者',
    `updater`       VARCHAR(64) COMMENT '更新者',
    `deleted`       INT      NOT NULL DEFAULT 0 COMMENT '是否删除',
    `uid`           bigint COMMENT '用户id',
    `content`       VARCHAR(500) COMMENT '内容',
    `likes`         bigint            DEFAULT 0 COMMENT '点赞数',
    `feedback_type` tinyint COMMENT '反馈类型',
    `avatar`        VARCHAR(512) COMMENT '头像',
    `user_type`     tinyint  NOT NULL COMMENT '用户类型',
    `nickname`      VARCHAR(30) COMMENT '用户昵称',
    PRIMARY KEY (id)
) COMMENT = '反馈表';

CREATE TABLE harbor_feedback_like
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `feedback_id` bigint COMMENT '反馈id',
    `uid`         bigint COMMENT '用户id',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `creator`     VARCHAR(64) COMMENT '创建者',
    `updater`     VARCHAR(64) COMMENT '更新者',
    `liked`       tinyint           DEFAULT 0 COMMENT '是否点赞',
    `deleted`     tinyint  NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id)
) COMMENT = '反馈点赞关联表';

INSERT INTO `harbor_app_user` (`id`, `create_time`, `update_time`, `creator`, `updater`, `deleted`, `user_type`, `username`, `password`, `user_open_id`, `avatar`, `nickname`, `status`) VALUES (1, '2023-08-14 11:26:14', '2023-08-14 11:26:14', NULL, NULL, 0, 0, 'test', '123456', NULL, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 'hehong', 0);