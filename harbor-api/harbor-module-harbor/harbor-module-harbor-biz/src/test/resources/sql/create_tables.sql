DROP TABLE IF EXISTS harbor_app_user;
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
    `user_open_id` VARCHAR(255) COMMENT '外部用户id',
    `avatar`       VARCHAR(512) COMMENT '头像',
    `nickname`     VARCHAR(30) COMMENT '用户昵称',
    `status`       tinyint           DEFAULT 0 COMMENT '用户状态',
    `tenant_id`    bigint COMMENT '产品编号',
    PRIMARY KEY (id)
) COMMENT = 'App用户表';

DROP TABLE IF EXISTS harbor_comment;
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
    `tenant_id`   bigint COMMENT '产品编号',
    `user_type`   tinyint  NOT NULL COMMENT '用户类型',
    `avatar`      VARCHAR(512) COMMENT '头像',
    `nickname`    VARCHAR(30) COMMENT '用户昵称',
    `imgs`        VARCHAR(1000) COMMENT '图片集',
    PRIMARY KEY (id)
) COMMENT = '评论表';

DROP TABLE IF EXISTS harbor_feedback;
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
    `tenant_id`     bigint COMMENT '产品编号',
    `avatar`        VARCHAR(512) COMMENT '头像',
    `user_type`     tinyint  NOT NULL COMMENT '用户类型',
    `nickname`      VARCHAR(30) COMMENT '用户昵称',
    `imgs`          VARCHAR(1000) COMMENT '图片集',
    PRIMARY KEY (id)
) COMMENT = '反馈表';

DROP TABLE IF EXISTS harbor_like;
CREATE TABLE harbor_like
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `creator`     VARCHAR(64) COMMENT '创建者',
    `updater`     VARCHAR(64) COMMENT '更新者',
    `deleted`     tinyint  NOT NULL DEFAULT 0 COMMENT '是否删除',
    `tenant_id`   bigint COMMENT '产品编号',
    `rid`         bigint COMMENT '关联id',
    `uid`         bigint COMMENT '用户id',
    `state`       tinyint COMMENT '点赞状态',
    `bus_type`    tinyint COMMENT '业务类型',
    PRIMARY KEY (id)
) COMMENT = '点赞表';

DROP TABLE IF EXISTS harbor_feedback_tag;
CREATE TABLE harbor_feedback_tag
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `creator`     VARCHAR(64) COMMENT '创建者',
    `updater`     VARCHAR(64) COMMENT '更新者',
    `deleted`     tinyint  NOT NULL DEFAULT 0 COMMENT '是否删除',
    `tenant_id`   bigint COMMENT '产品编号',
    `name_ch`     VARCHAR(255) COMMENT '标签名中文',
    `name_en`     VARCHAR(255) COMMENT '标签名英语',
    `sort`       int COMMENT '标签顺序',
    `color`       VARCHAR(255) COMMENT '标签颜色',
    PRIMARY KEY (id)
) COMMENT = '反馈标签';

