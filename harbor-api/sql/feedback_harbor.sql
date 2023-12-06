/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.10.100_3306
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 192.168.10.100:3306
 Source Schema         : ruoyi-vue-pro

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 04/12/2023 16:59:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('schedulerName', 'FeedbackLikeJob', 'DEFAULT', '0 0 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME` ASC, `INSTANCE_NAME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME` ASC, `INSTANCE_NAME` ASC, `REQUESTS_RECOVERY` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME` ASC, `REQUESTS_RECOVERY` ASC) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('schedulerName', 'FeedbackLikeJob', 'DEFAULT', NULL, 'cn.hh.harbor.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000147400104A4F425F48414E444C45525F4E414D4574000F466565646261636B4C696B654A6F627800);

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('schedulerName', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('schedulerName', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('schedulerName', 'hehong1701677651268', 1701679766748, 15000);

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `INT_PROP_1` int NULL DEFAULT NULL,
  `INT_PROP_2` int NULL DEFAULT NULL,
  `LONG_PROP_1` bigint NULL DEFAULT NULL,
  `LONG_PROP_2` bigint NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PRIORITY` int NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME` ASC, `CALENDAR_NAME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME` ASC, `TRIGGER_STATE` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('schedulerName', 'FeedbackLikeJob', 'DEFAULT', 'FeedbackLikeJob', 'DEFAULT', NULL, 1701680400000, 1701677666505, 5, 'WAITING', 'CRON', 1698728554000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E5471007E000C7800);

-- ----------------------------
-- Table structure for harbor_comment
-- ----------------------------
DROP TABLE IF EXISTS `harbor_comment`;
CREATE TABLE `harbor_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父节点',
  `uid` bigint NULL DEFAULT NULL COMMENT '用户id',
  `feedback_id` bigint NULL DEFAULT NULL COMMENT '反馈id',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `likes` bigint NULL DEFAULT 0 COMMENT '点赞数',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '产品编号',
  `imgs` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片集;',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725926281090736131 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of harbor_comment
-- ----------------------------

-- ----------------------------
-- Table structure for harbor_feedback
-- ----------------------------
DROP TABLE IF EXISTS `harbor_feedback`;
CREATE TABLE `harbor_feedback`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  `uid` bigint NULL DEFAULT NULL COMMENT '用户id',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `likes` bigint NULL DEFAULT 0 COMMENT '点赞数',
  `feedback_tag_id` bigint NULL DEFAULT NULL COMMENT '反馈标签id;',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '产品编号',
  `imgs` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片集;',
  `reply_state` tinyint NULL DEFAULT NULL COMMENT '回复状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725920663843987458 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '反馈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of harbor_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for harbor_feedback_tag
-- ----------------------------
DROP TABLE IF EXISTS `harbor_feedback_tag`;
CREATE TABLE `harbor_feedback_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '产品编号',
  `name_ch` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名中文',
  `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名英语',
  `sort` int NULL DEFAULT NULL COMMENT '标签顺序;',
  `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签颜色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100748 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '反馈标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of harbor_feedback_tag
-- ----------------------------
INSERT INTO `harbor_feedback_tag` VALUES (1, '2023-10-06 14:34:48', '2023-10-21 14:03:29', '1', '1', 0, 1, '问题反馈', 'Problem feedback', 1, '#FFD700');
INSERT INTO `harbor_feedback_tag` VALUES (2, '2023-10-06 18:00:06', '2023-10-06 18:01:54', '1', '1', 0, 1, '产品建议', 'Product suggestion', 2, '#90EE90');
INSERT INTO `harbor_feedback_tag` VALUES (4, '2023-10-06 18:00:50', '2023-10-21 14:08:18', '1', '1', 0, 1, '功能添加', 'Function addition', 3, '#00CED1');

-- ----------------------------
-- Table structure for harbor_like
-- ----------------------------
DROP TABLE IF EXISTS `harbor_like`;
CREATE TABLE `harbor_like`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  `rid` bigint NULL DEFAULT NULL COMMENT '关联id',
  `uid` bigint NULL DEFAULT NULL COMMENT '用户id',
  `state` tinyint NULL DEFAULT NULL COMMENT '点赞状态',
  `bus_type` tinyint NULL DEFAULT NULL COMMENT '业务类型;',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100198 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '点赞表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of harbor_like
-- ----------------------------

-- ----------------------------
-- Table structure for infra_api_access_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_api_access_log`;
CREATE TABLE `infra_api_access_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
  `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求地址',
  `request_params` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求参数',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `begin_time` datetime NOT NULL COMMENT '开始请求时间',
  `end_time` datetime NOT NULL COMMENT '结束请求时间',
  `duration` int NOT NULL COMMENT '执行时长',
  `result_code` int NOT NULL DEFAULT 0 COMMENT '结果码',
  `result_msg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果提示',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101335 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'API 访问日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_api_access_log
-- ----------------------------

-- ----------------------------
-- Table structure for infra_api_error_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_api_error_log`;
CREATE TABLE `infra_api_error_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '链路追踪编号\n     *\n     * 一般来说，通过链路追踪编号，可以将访问日志，错误日志，链路追踪日志，logger 打印日志等，结合在一起，从而进行排错。',
  `user_id` int NOT NULL DEFAULT 0 COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
  `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名\n     *\n     * 目前读取 spring.application.name',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求地址',
  `request_params` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `exception_time` datetime NOT NULL COMMENT '异常发生时间',
  `exception_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '异常名\n     *\n     * {@link Throwable#getClass()} 的类全名',
  `exception_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常导致的消息\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getMessage(Throwable)}',
  `exception_root_cause_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常导致的根消息\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getRootCauseMessage(Throwable)}',
  `exception_stack_trace` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常的栈轨迹\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getServiceException(Exception)}',
  `exception_class_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常发生的类全名\n     *\n     * {@link StackTraceElement#getClassName()}',
  `exception_file_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常发生的类文件\n     *\n     * {@link StackTraceElement#getFileName()}',
  `exception_method_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常发生的方法名\n     *\n     * {@link StackTraceElement#getMethodName()}',
  `exception_line_number` int NOT NULL COMMENT '异常发生的方法所在行\n     *\n     * {@link StackTraceElement#getLineNumber()}',
  `process_status` tinyint NOT NULL COMMENT '处理状态',
  `process_time` datetime NULL DEFAULT NULL COMMENT '处理时间',
  `process_user_id` int NULL DEFAULT 0 COMMENT '处理用户编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101169 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统异常日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_api_error_log
-- ----------------------------

-- ----------------------------
-- Table structure for infra_codegen_column
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_column`;
CREATE TABLE `infra_codegen_column`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint NOT NULL COMMENT '表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段名',
  `data_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段类型',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段描述',
  `nullable` bit(1) NOT NULL COMMENT '是否允许为空',
  `primary_key` bit(1) NOT NULL COMMENT '是否主键',
  `auto_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否自增',
  `ordinal_position` int NOT NULL COMMENT '排序',
  `java_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Java 属性类型',
  `java_field` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Java 属性名',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `example` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据示例',
  `create_operation` bit(1) NOT NULL COMMENT '是否为 Create 创建操作的字段',
  `update_operation` bit(1) NOT NULL COMMENT '是否为 Update 更新操作的字段',
  `list_operation` bit(1) NOT NULL COMMENT '是否为 List 查询操作的字段',
  `list_operation_condition` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '=' COMMENT 'List 查询操作的条件类型',
  `list_operation_result` bit(1) NOT NULL COMMENT '是否为 List 查询操作的返回字段',
  `html_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '显示类型',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1839 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代码生成表字段定义' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_codegen_column
-- ----------------------------
INSERT INTO `infra_codegen_column` VALUES (1715, 132, 'id', 'BIGINT', '编号', b'0', b'1', '1', 1, 'Long', 'id', '', '20558', b'0', b'1', b'0', '=', b'1', 'input', '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1716, 132, 'name', 'VARCHAR', '名字', b'0', b'0', '0', 2, 'String', 'name', '', '芋艿', b'1', b'1', b'1', 'LIKE', b'1', 'input', '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1717, 132, 'description', 'VARCHAR', '描述', b'1', b'0', '0', 3, 'String', 'description', '', '随便', b'1', b'1', b'0', '=', b'1', 'editor', '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1718, 132, 'status', 'TINYINT', '状态', b'0', b'0', '0', 4, 'Byte', 'status', 'common_status', '0', b'1', b'1', b'1', '=', b'1', 'select', '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1719, 132, 'creator', 'VARCHAR', '创建者', b'1', b'0', '0', 5, 'String', 'creator', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1720, 132, 'create_time', 'TIMESTAMP', '创建时间', b'0', b'0', '0', 6, 'LocalDateTime', 'createTime', '', NULL, b'0', b'0', b'1', 'BETWEEN', b'1', 'datetime', '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1721, 132, 'updater', 'VARCHAR', '更新者', b'1', b'0', '0', 7, 'String', 'updater', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1722, 132, 'update_time', 'TIMESTAMP', '更新时间', b'0', b'0', '0', 8, 'LocalDateTime', 'updateTime', '', NULL, b'0', b'0', b'0', 'BETWEEN', b'0', 'datetime', '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1723, 132, 'deleted', 'BIT', '是否删除', b'0', b'0', '0', 9, 'Boolean', 'deleted', '', NULL, b'0', b'0', b'0', '=', b'0', 'radio', '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1724, 132, 'tenant_id', 'BIGINT', '租户编号', b'0', b'0', '0', 10, 'Long', 'tenantId', '', '32479', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1725, 133, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '16238', b'0', b'1', b'0', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1726, 133, 'create_time', 'TIMESTAMP', '创建时间', b'0', b'0', '0', 2, 'LocalDateTime', 'createTime', '', NULL, b'0', b'0', b'1', 'BETWEEN', b'1', 'datetime', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1727, 133, 'update_time', 'TIMESTAMP', '最后更新时间', b'0', b'0', '0', 3, 'LocalDateTime', 'updateTime', '', NULL, b'0', b'0', b'0', 'BETWEEN', b'0', 'datetime', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1728, 133, 'creator', 'VARCHAR', '创建者', b'1', b'0', '0', 4, 'String', 'creator', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1729, 133, 'updater', 'VARCHAR', '更新者', b'1', b'0', '0', 5, 'String', 'updater', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1730, 133, 'deleted', 'TINYINT', '是否删除', b'0', b'0', '0', 6, 'Byte', 'deleted', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1731, 133, 'user_type', 'TINYINT', '用户类型', b'0', b'0', '0', 7, 'Byte', 'userType', 'uservoice_app_user_type', '1', b'1', b'1', b'1', '=', b'1', 'select', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1732, 133, 'username', 'VARCHAR', '用户账号', b'1', b'0', '0', 8, 'String', 'username', '', '芋艿', b'1', b'1', b'1', 'LIKE', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1733, 133, 'password', 'VARCHAR', '密码', b'1', b'0', '0', 9, 'String', 'password', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1734, 133, 'user_open_id', 'VARCHAR', '登录态用户id', b'1', b'0', '0', 10, 'String', 'userOpenId', '', '22660', b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1735, 133, 'avatar', 'VARCHAR', '头像', b'1', b'0', '0', 11, 'String', 'avatar', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1736, 133, 'nickname', 'VARCHAR', '用户昵称', b'1', b'0', '0', 12, 'String', 'nickname', '', '李四', b'1', b'1', b'1', 'LIKE', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1737, 133, 'status', 'TINYINT', '用户状态', b'1', b'0', '0', 13, 'Byte', 'status', 'uservoice_app_user_status', '1', b'1', b'1', b'1', '=', b'1', 'radio', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1738, 133, 'tenant_id', 'BIGINT', '租户编号(产品编号)', b'1', b'0', '0', 14, 'Long', 'tenantId', '', '15613', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1739, 134, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '22079', b'0', b'1', b'0', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1740, 134, 'create_time', 'TIMESTAMP', '创建时间', b'0', b'0', '0', 2, 'LocalDateTime', 'createTime', '', NULL, b'0', b'0', b'1', 'BETWEEN', b'1', 'datetime', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1741, 134, 'update_time', 'TIMESTAMP', '最后更新时间', b'0', b'0', '0', 3, 'LocalDateTime', 'updateTime', '', NULL, b'0', b'0', b'0', 'BETWEEN', b'0', 'datetime', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1742, 134, 'creator', 'VARCHAR', '创建者', b'1', b'0', '0', 4, 'String', 'creator', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1743, 134, 'updater', 'VARCHAR', '更新者', b'1', b'0', '0', 5, 'String', 'updater', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1744, 134, 'deleted', 'INTEGER', '是否删除', b'0', b'0', '0', 6, 'Integer', 'deleted', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1745, 134, 'parent_id', 'BIGINT', '父节点', b'1', b'0', '0', 7, 'Long', 'parentId', '', '524', b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1746, 134, 'uid', 'BIGINT', '用户id', b'1', b'0', '0', 8, 'Long', 'uid', '', '21744', b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1747, 134, 'feedback_id', 'BIGINT', '反馈id', b'1', b'0', '0', 9, 'Long', 'feedbackId', '', '21567', b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1748, 134, 'content', 'VARCHAR', '内容', b'1', b'0', '0', 10, 'String', 'content', '', NULL, b'1', b'1', b'1', '=', b'1', 'editor', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1749, 134, 'likes', 'BIGINT', '点赞数', b'1', b'0', '0', 11, 'Long', 'likes', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1750, 134, 'tenant_id', 'BIGINT', '租户编号(产品编号)', b'1', b'0', '0', 12, 'Long', 'tenantId', '', '27814', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1751, 135, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '28664', b'0', b'1', b'0', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:15', b'1');
INSERT INTO `infra_codegen_column` VALUES (1752, 135, 'comment_id', 'BIGINT', '评论id', b'1', b'0', '0', 2, 'Long', 'commentId', '', '30114', b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:15', b'1');
INSERT INTO `infra_codegen_column` VALUES (1753, 135, 'uid', 'BIGINT', '用户id', b'1', b'0', '0', 3, 'Long', 'uid', '', '30745', b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:15', b'1');
INSERT INTO `infra_codegen_column` VALUES (1754, 135, 'tenant_id', 'BIGINT', '租户编号(产品编号)', b'1', b'0', '0', 4, 'Long', 'tenantId', '', '18544', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:15', b'1');
INSERT INTO `infra_codegen_column` VALUES (1755, 136, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '6795', b'0', b'1', b'0', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1756, 136, 'create_time', 'TIMESTAMP', '创建时间', b'0', b'0', '0', 2, 'LocalDateTime', 'createTime', '', NULL, b'0', b'0', b'1', 'BETWEEN', b'1', 'datetime', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1757, 136, 'update_time', 'TIMESTAMP', '最后更新时间', b'0', b'0', '0', 3, 'LocalDateTime', 'updateTime', '', NULL, b'0', b'0', b'0', 'BETWEEN', b'0', 'datetime', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1758, 136, 'creator', 'VARCHAR', '创建者', b'1', b'0', '0', 4, 'String', 'creator', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1759, 136, 'updater', 'VARCHAR', '更新者', b'1', b'0', '0', 5, 'String', 'updater', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1760, 136, 'deleted', 'INTEGER', '是否删除', b'0', b'0', '0', 6, 'Integer', 'deleted', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1761, 136, 'uid', 'BIGINT', '用户id', b'1', b'0', '0', 7, 'Long', 'uid', '', '16979', b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1762, 136, 'content', 'VARCHAR', '内容', b'1', b'0', '0', 8, 'String', 'content', '', NULL, b'1', b'1', b'1', '=', b'1', 'editor', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1763, 136, 'likes', 'BIGINT', '点赞数', b'1', b'0', '0', 9, 'Long', 'likes', '', NULL, b'0', b'0', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1764, 136, 'feedback_type', 'TINYINT', '反馈类型', b'1', b'0', '0', 10, 'Integer', 'feedbackType', '', '1', b'1', b'1', b'1', '=', b'1', 'select', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1765, 136, 'tenant_id', 'BIGINT', '租户编号(产品编号)', b'1', b'0', '0', 11, 'Long', 'tenantId', '', '32151', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_column` VALUES (1766, 137, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '23701', b'0', b'1', b'0', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:18', b'1');
INSERT INTO `infra_codegen_column` VALUES (1767, 137, 'feedback_id', 'BIGINT', '反馈id', b'1', b'0', '0', 2, 'Long', 'feedbackId', '', '7969', b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:18', b'1');
INSERT INTO `infra_codegen_column` VALUES (1768, 137, 'uid', 'BIGINT', '用户id', b'1', b'0', '0', 3, 'Long', 'uid', '', '23606', b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:18', b'1');
INSERT INTO `infra_codegen_column` VALUES (1769, 137, 'tenant_id', 'BIGINT', '租户编号(产品编号)', b'1', b'0', '0', 4, 'Long', 'tenantId', '', '5219', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:18', b'1');
INSERT INTO `infra_codegen_column` VALUES (1770, 134, 'user_type', 'TINYINT', '用户类型;', b'0', b'0', '0', 1, 'Integer', 'userType', '', '2', b'1', b'1', b'1', '=', b'1', 'select', '1', '2023-08-26 20:50:21', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1771, 134, 'avatar', 'VARCHAR', '头像;', b'1', b'0', '0', 2, 'String', 'avatar', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-08-26 20:50:21', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1772, 134, 'nickname', 'VARCHAR', '用户昵称;', b'1', b'0', '0', 3, 'String', 'nickname', '', '赵六', b'1', b'1', b'1', 'LIKE', b'1', 'input', '1', '2023-08-26 20:50:21', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_column` VALUES (1773, 138, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '21851', b'0', b'1', b'0', '=', b'1', 'input', '1', '2023-10-05 15:20:23', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_column` VALUES (1774, 138, 'create_time', 'TIMESTAMP', '创建时间', b'0', b'0', '0', 2, 'LocalDateTime', 'createTime', '', NULL, b'0', b'0', b'1', 'BETWEEN', b'1', 'datetime', '1', '2023-10-05 15:20:23', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_column` VALUES (1775, 138, 'update_time', 'TIMESTAMP', '最后更新时间', b'0', b'0', '0', 3, 'LocalDateTime', 'updateTime', '', NULL, b'0', b'0', b'0', 'BETWEEN', b'0', 'datetime', '1', '2023-10-05 15:20:23', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_column` VALUES (1776, 138, 'creator', 'VARCHAR', '创建者', b'1', b'0', '0', 4, 'String', 'creator', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 15:20:23', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_column` VALUES (1777, 138, 'updater', 'VARCHAR', '更新者', b'1', b'0', '0', 5, 'String', 'updater', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 15:20:23', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_column` VALUES (1778, 138, 'deleted', 'TINYINT', '是否删除', b'0', b'0', '0', 6, 'Byte', 'deleted', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 15:20:23', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_column` VALUES (1779, 138, 'tenant_id', 'BIGINT', '产品编号', b'1', b'0', '0', 7, 'Long', 'tenantId', '', '27221', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 15:20:23', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_column` VALUES (1780, 138, 'name_ch', 'VARCHAR', '标签名中文', b'1', b'0', '0', 8, 'String', 'nameCh', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 15:20:23', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_column` VALUES (1781, 138, 'name_en', 'VARCHAR', '标签名英语', b'1', b'0', '0', 9, 'String', 'nameEn', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 15:20:23', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_column` VALUES (1782, 138, 'order', 'BIGINT', '标签顺序', b'1', b'0', '0', 10, 'Long', 'order', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 15:20:23', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_column` VALUES (1783, 139, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '1739', b'0', b'1', b'0', '=', b'1', 'input', '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_column` VALUES (1784, 139, 'create_time', 'TIMESTAMP', '创建时间', b'0', b'0', '0', 2, 'LocalDateTime', 'createTime', '', NULL, b'0', b'0', b'1', 'BETWEEN', b'1', 'datetime', '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_column` VALUES (1785, 139, 'update_time', 'TIMESTAMP', '最后更新时间', b'0', b'0', '0', 3, 'LocalDateTime', 'updateTime', '', NULL, b'0', b'0', b'0', 'BETWEEN', b'0', 'datetime', '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_column` VALUES (1786, 139, 'creator', 'VARCHAR', '创建者', b'1', b'0', '0', 4, 'String', 'creator', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_column` VALUES (1787, 139, 'updater', 'VARCHAR', '更新者', b'1', b'0', '0', 5, 'String', 'updater', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_column` VALUES (1788, 139, 'deleted', 'TINYINT', '是否删除', b'0', b'0', '0', 6, 'Byte', 'deleted', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_column` VALUES (1789, 139, 'tenant_id', 'BIGINT', '产品编号', b'1', b'0', '0', 7, 'Long', 'tenantId', '', '21863', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_column` VALUES (1790, 139, 'name_ch', 'VARCHAR', '标签名中文', b'1', b'0', '0', 8, 'String', 'nameCh', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_column` VALUES (1791, 139, 'name_en', 'VARCHAR', '标签名英语', b'1', b'0', '0', 9, 'String', 'nameEn', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_column` VALUES (1792, 139, 'order', 'BIGINT', '标签顺序', b'1', b'0', '0', 10, 'Long', 'order', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_column` VALUES (1793, 140, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '32447', b'0', b'1', b'0', '=', b'1', 'input', '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_column` VALUES (1794, 140, 'create_time', 'TIMESTAMP', '创建时间', b'0', b'0', '0', 2, 'LocalDateTime', 'createTime', '', NULL, b'0', b'0', b'1', 'BETWEEN', b'1', 'datetime', '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_column` VALUES (1795, 140, 'update_time', 'TIMESTAMP', '最后更新时间', b'0', b'0', '0', 3, 'LocalDateTime', 'updateTime', '', NULL, b'0', b'0', b'0', 'BETWEEN', b'0', 'datetime', '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_column` VALUES (1796, 140, 'creator', 'VARCHAR', '创建者', b'1', b'0', '0', 4, 'String', 'creator', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_column` VALUES (1797, 140, 'updater', 'VARCHAR', '更新者', b'1', b'0', '0', 5, 'String', 'updater', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_column` VALUES (1798, 140, 'deleted', 'TINYINT', '是否删除', b'0', b'0', '0', 6, 'Byte', 'deleted', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_column` VALUES (1799, 140, 'tenant_id', 'BIGINT', '产品编号', b'1', b'0', '0', 7, 'Long', 'tenantId', '', '27638', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_column` VALUES (1800, 140, 'name_ch', 'VARCHAR', '标签名中文', b'1', b'0', '0', 8, 'String', 'nameCh', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_column` VALUES (1801, 140, 'name_en', 'VARCHAR', '标签名英语', b'1', b'0', '0', 9, 'String', 'nameEn', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_column` VALUES (1802, 140, 'order', 'BIGINT', '标签顺序', b'1', b'0', '0', 10, 'Long', 'order', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_column` VALUES (1803, 141, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '24785', b'0', b'1', b'0', '=', b'1', 'input', '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1804, 141, 'create_time', 'TIMESTAMP', '创建时间', b'0', b'0', '0', 2, 'LocalDateTime', 'createTime', '', NULL, b'0', b'0', b'1', 'BETWEEN', b'1', 'datetime', '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1805, 141, 'update_time', 'TIMESTAMP', '最后更新时间', b'0', b'0', '0', 3, 'LocalDateTime', 'updateTime', '', NULL, b'0', b'0', b'0', 'BETWEEN', b'0', 'datetime', '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1806, 141, 'creator', 'VARCHAR', '创建者', b'1', b'0', '0', 4, 'String', 'creator', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1807, 141, 'updater', 'VARCHAR', '更新者', b'1', b'0', '0', 5, 'String', 'updater', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1808, 141, 'deleted', 'TINYINT', '是否删除', b'0', b'0', '0', 6, 'Byte', 'deleted', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1809, 141, 'tenant_id', 'BIGINT', '产品编号', b'1', b'0', '0', 7, 'Long', 'tenantId', '', '3159', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1810, 141, 'name_ch', 'VARCHAR', '标签名中文', b'1', b'0', '0', 8, 'String', 'nameCh', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1811, 141, 'name_en', 'VARCHAR', '标签名英语', b'1', b'0', '0', 9, 'String', 'nameEn', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1812, 141, 'order', 'BIGINT', '标签顺序', b'1', b'0', '0', 10, 'Long', 'order', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_column` VALUES (1813, 142, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '', b'0', b'0', b'1', '=', b'1', 'input', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1814, 142, 'create_time', 'TIMESTAMP', '创建时间', b'0', b'0', '0', 2, 'LocalDateTime', 'createTime', '', NULL, b'0', b'0', b'1', 'BETWEEN', b'1', 'datetime', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1815, 142, 'update_time', 'TIMESTAMP', '最后更新时间', b'0', b'0', '0', 3, 'LocalDateTime', 'updateTime', '', NULL, b'0', b'0', b'0', 'BETWEEN', b'0', 'datetime', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1816, 142, 'creator', 'VARCHAR', '创建者', b'1', b'0', '0', 4, 'String', 'creator', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1817, 142, 'updater', 'VARCHAR', '更新者', b'1', b'0', '0', 5, 'String', 'updater', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1818, 142, 'deleted', 'TINYINT', '是否删除', b'0', b'0', '0', 6, 'Byte', 'deleted', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1819, 142, 'tenant_id', 'BIGINT', '产品编号', b'1', b'0', '0', 7, 'Long', 'tenantId', '', '', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1820, 142, 'name_ch', 'VARCHAR', '标签名中文', b'1', b'0', '0', 8, 'String', 'nameCh', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1821, 142, 'name_en', 'VARCHAR', '标签名英语', b'1', b'0', '0', 9, 'String', 'nameEn', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1822, 142, 'order', 'BIGINT', '标签顺序', b'1', b'0', '0', 10, 'Long', 'order', '', NULL, b'1', b'1', b'1', '=', b'1', 'input', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1823, 142, 'color', 'VARCHAR', '标签颜色', b'1', b'0', '0', 11, 'String', 'color', '', NULL, b'1', b'1', b'0', '=', b'1', 'input', '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_column` VALUES (1824, 143, 'id', 'BIGINT', '主键', b'0', b'1', '1', 1, 'Long', 'id', '', '', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1825, 143, 'create_time', 'TIMESTAMP', '创建时间', b'0', b'0', '0', 2, 'LocalDateTime', 'createTime', '', NULL, b'0', b'0', b'1', 'BETWEEN', b'1', 'datetime', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1826, 143, 'update_time', 'TIMESTAMP', '最后更新时间', b'0', b'0', '0', 3, 'LocalDateTime', 'updateTime', '', NULL, b'0', b'0', b'0', 'BETWEEN', b'0', 'datetime', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1827, 143, 'creator', 'VARCHAR', '创建者', b'1', b'0', '0', 4, 'String', 'creator', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1828, 143, 'updater', 'VARCHAR', '更新者', b'1', b'0', '0', 5, 'String', 'updater', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1829, 143, 'deleted', 'INTEGER', '是否删除', b'0', b'0', '0', 6, 'Integer', 'deleted', '', NULL, b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1830, 143, 'uid', 'BIGINT', '用户id', b'1', b'0', '0', 7, 'Long', 'uid', '', '', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1831, 143, 'content', 'LONGVARCHAR', '内容', b'1', b'0', '0', 8, 'String', 'content', '', NULL, b'1', b'0', b'1', '=', b'1', 'editor', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1832, 143, 'likes', 'BIGINT', '点赞数', b'1', b'0', '0', 9, 'Long', 'likes', '', NULL, b'0', b'0', b'0', '=', b'1', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1833, 143, 'feedback_tag_id', 'BIGINT', '反馈标签id', b'1', b'0', '0', 10, 'Long', 'feedbackTagId', '', '', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1834, 143, 'tenant_id', 'BIGINT', '产品编号', b'1', b'0', '0', 11, 'Long', 'tenantId', '', '', b'0', b'0', b'0', '=', b'0', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1835, 143, 'avatar', 'VARCHAR', '头像', b'1', b'0', '0', 12, 'String', 'avatar', '', NULL, b'0', b'0', b'1', '=', b'1', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1836, 143, 'user_type', 'TINYINT', '用户类型', b'0', b'0', '0', 13, 'Integer', 'userType', '', '', b'0', b'0', b'1', '=', b'1', 'select', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1837, 143, 'nickname', 'VARCHAR', '用户昵称', b'1', b'0', '0', 14, 'String', 'nickname', '', '', b'0', b'0', b'1', 'LIKE', b'1', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1838, 143, 'imgs', 'VARCHAR', '图片集', b'1', b'0', '0', 15, 'String', 'imgs', '', NULL, b'0', b'0', b'0', '=', b'1', 'input', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');
INSERT INTO `infra_codegen_column` VALUES (1839, 143, 'state', 'TINYINT', '反馈状态', b'1', b'0', '0', 16, 'Integer', 'state', '', NULL, b'0', b'0', b'1', '=', b'1', 'select', '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');

-- ----------------------------
-- Table structure for infra_codegen_table
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_table`;
CREATE TABLE `infra_codegen_table`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `data_source_config_id` bigint NOT NULL COMMENT '数据源配置的编号',
  `scene` tinyint NOT NULL DEFAULT 1 COMMENT '生成场景',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表描述',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '类名称',
  `class_comment` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类描述',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '作者',
  `template_type` tinyint NOT NULL DEFAULT 1 COMMENT '模板类型',
  `front_type` tinyint NOT NULL COMMENT '前端类型',
  `parent_menu_id` bigint NULL DEFAULT NULL COMMENT '父菜单编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 143 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代码生成表定义' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_codegen_table
-- ----------------------------
INSERT INTO `infra_codegen_table` VALUES (132, 0, 1, 'system_group', '用户组', NULL, 'system', 'group', 'Group', '用户组', 'hehong', 1, 20, 1, '1', '2023-08-05 19:15:00', '1', '2023-08-13 23:23:14', b'1');
INSERT INTO `infra_codegen_table` VALUES (133, 0, 1, 'uservoice_app_user', 'App用户表', NULL, 'uservoice', 'appuser', 'AppUser', 'App用户', 'hehong', 1, 20, 2309, '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:11', b'1');
INSERT INTO `infra_codegen_table` VALUES (134, 0, 1, 'uservoice_comment', '评论表', NULL, 'uservoice', 'comment', 'Comment', '评论', 'hehong', 1, 10, 2310, '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:14', b'1');
INSERT INTO `infra_codegen_table` VALUES (135, 0, 1, 'uservoice_comment_like', '评论点赞关联表', NULL, 'uservoice', 'commentlike', 'CommentLike', '评论点赞关联', '芋道源码', 1, 10, NULL, '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:15', b'1');
INSERT INTO `infra_codegen_table` VALUES (136, 0, 2, 'uservoice_feedback', '反馈表', NULL, 'uservoice', 'feedback', 'Feedback', '用户反馈', '芋道源码', 1, 20, 2310, '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:17', b'1');
INSERT INTO `infra_codegen_table` VALUES (137, 0, 1, 'uservoice_feedback_like', '反馈点赞关联表', NULL, 'uservoice', 'feedbacklike', 'FeedbackLike', '反馈点赞关联', '芋道源码', 1, 10, NULL, '1', '2023-08-14 20:20:43', '1', '2023-10-05 15:20:18', b'1');
INSERT INTO `infra_codegen_table` VALUES (138, 0, 1, 'feedback_tag', '反馈标签', NULL, 'feedback', 'tag', 'Tag', '反馈标签', '芋道源码', 1, 10, NULL, '1', '2023-10-05 15:20:22', '1', '2023-10-05 17:18:32', b'1');
INSERT INTO `infra_codegen_table` VALUES (139, 0, 1, 'feedback_tag', '反馈标签', NULL, 'feedback', 'tag', 'Tag', '反馈标签', '鸿雁', 1, 10, NULL, '1', '2023-10-05 17:18:35', '1', '2023-10-05 17:24:21', b'1');
INSERT INTO `infra_codegen_table` VALUES (140, 0, 1, 'feedback_tag', '反馈标签', NULL, 'feedback', 'tag', 'Tag', '反馈标签', 'hehong', 1, 10, NULL, '1', '2023-10-05 17:24:24', '1', '2023-10-05 17:29:29', b'1');
INSERT INTO `infra_codegen_table` VALUES (141, 0, 1, 'feedback_tag', '反馈标签', NULL, 'feedback', 'tag', 'Tag', '反馈标签', 'hehong', 1, 10, NULL, '1', '2023-10-05 17:29:33', '1', '2023-10-05 17:31:11', b'1');
INSERT INTO `infra_codegen_table` VALUES (142, 0, 1, 'harbor_feedback_tag', '反馈标签', NULL, 'harbor', 'feedbacktag', 'FeedbackTag', '反馈标签', 'hehong', 1, 21, 2311, '1', '2023-10-05 17:31:15', '1', '2023-10-05 19:25:37', b'0');
INSERT INTO `infra_codegen_table` VALUES (143, 0, 1, 'harbor_feedback', '反馈表', NULL, 'harbor', 'feedback', 'Feedback', '反馈', 'hehong', 1, 20, 2311, '1', '2023-10-11 15:45:00', '1', '2023-10-11 16:01:25', b'0');

-- ----------------------------
-- Table structure for infra_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_config`;
CREATE TABLE `infra_config`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数分组',
  `type` tinyint NOT NULL COMMENT '参数类型',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键名',
  `value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键值',
  `visible` bit(1) NOT NULL COMMENT '是否可见',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_config
-- ----------------------------
INSERT INTO `infra_config` VALUES (7, 'url', 2, 'MySQL 监控的地址', 'url.druid', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:33:38', b'0');
INSERT INTO `infra_config` VALUES (8, 'url', 2, 'SkyWalking 监控的地址', 'url.skywalking', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:57:03', b'0');
INSERT INTO `infra_config` VALUES (9, 'url', 2, 'Spring Boot Admin 监控的地址', 'url.spring-boot-admin', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:52:07', b'0');
INSERT INTO `infra_config` VALUES (10, 'url', 2, 'Swagger 接口文档的地址', 'url.swagger', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:59:00', b'0');

-- ----------------------------
-- Table structure for infra_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_data_source_config`;
CREATE TABLE `infra_data_source_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源连接',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '数据源配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_data_source_config
-- ----------------------------

-- ----------------------------
-- Table structure for infra_file
-- ----------------------------
DROP TABLE IF EXISTS `infra_file`;
CREATE TABLE `infra_file`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件编号',
  `config_id` bigint NULL DEFAULT NULL COMMENT '配置编号',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件名',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件 URL',
  `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` int NOT NULL COMMENT '文件大小',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725856250638151681 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_file
-- ----------------------------
INSERT INTO `infra_file` VALUES (1, 4, 'Logo.png', 'Logo.png', 'http://127.0.0.1:48080/admin-api/infra/file/4/get/Logo.png', 'image/png', 5996, '1', '2023-11-11 18:08:52', '1', '2023-12-04 16:50:30', b'0');

-- ----------------------------
-- Table structure for infra_file_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_config`;
CREATE TABLE `infra_file_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名',
  `storage` tinyint NOT NULL COMMENT '存储器',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `master` bit(1) NOT NULL COMMENT '是否为主配置',
  `config` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储配置',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_file_config
-- ----------------------------
INSERT INTO `infra_file_config` VALUES (4, '数据库', 1, '我是数据库', b'1', '{\"@class\":\"cn.hh.harbor.framework.file.core.client.db.DBFileClientConfig\",\"domain\":\"http://127.0.0.1:48080\"}', '1', '2022-03-15 23:56:24', '1', '2023-11-21 13:58:28', b'0');
INSERT INTO `infra_file_config` VALUES (5, '本地磁盘', 10, '测试下本地存储', b'0', '{\"@class\":\"cn.hh.harbor.framework.file.core.client.local.LocalFileClientConfig\",\"basePath\":\"/Users/yunai/file_test\",\"domain\":\"http://127.0.0.1:48080\"}', '1', '2022-03-15 23:57:00', '1', '2023-11-21 13:58:50', b'0');

-- ----------------------------
-- Table structure for infra_file_content
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_content`;
CREATE TABLE `infra_file_content`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `config_id` bigint NOT NULL COMMENT '配置编号',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
  `content` mediumblob NOT NULL COMMENT '文件内容',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 257 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_file_content
-- ----------------------------
INSERT INTO `infra_file_content` VALUES (1, 4, 'Logo.png', 0x89504E470D0A1A0A0000000D49484452000000C8000000C80806000000AD58AE9E000000017352474200AECE1CE90000172649444154785EED9D5D6EDDB6128029C50BB840FA5E27ED3E62AF24F12E5AA34063A070EF2E62AF24CE3EDAF4F43D01EE026CF182F4A1AC738E240E8733E490E27969034B14399C8FF3C3BF4EB55F1209FCE7FC9773F3A1B3B3B373AD87F34E2BFB6FADD48F4B15E894FA77FA37DDA99DF977D7F5BBC7C7C7DDFF76FFB5FF6E3F3E09747C456FAF6403C159DF7FB08AAFF53BD57506020B02D3EF1910AD77AAEB760628039101E8DB5F7F3C307D7353C5364010DD6D41383B3B57C3703181C1FEBFA09FB32E0FAA535F9E9E86876671C27BA701029499B30E5AA9F7CC56015823D463061A0B4CB33230F9354016E474E02E29F53B4C9CC53D6581E9FAFEBEB964F37DD70099C865B412CFF1833497899BBE06CB8C84370F4825AE13353C0D96BD44370BC8048C5ADD272A684C76ECFEDBD7DB8F54059654CEE600F9E1EDF5C7C203ED5CFA6541791C86BB2D65C3360148B316E44CDD6D25B0AF1A9006063918C70556EF7E550BC8DE956AF1053B23F603BBAEEFAF6A4C1557074803230D11B35FD1FAE149EBAB9A62946A0069C17746300E3F5D55305F3C2026CE78D5759F3638B1278688858A54119F140D4873A7A433B28F4F0A9E4729129066358A00E3A0929D5237254E36160748A556636DE313E77E92D4A41597ED2A06106B35FAFE73614BCDC73D196E77A0DBD06434139A169DEE4634EF991D8956B3B57AA7B43E2F2CFE2A2A3629029042ACC6CEECECEBBAEE4B8E5D7D6E139785C780A394DDD928F8B77B1A864BE92961D180088F35EC48682D81D0857C567EAFFA8B3D3066F9BE34774DBC35110B885097EA198ABE7F80BA47924670A9FB5D2407F02201F9E1E7DF2EF430987843C2AFAA892F27D0D1BA0CFABD901846A4CB250E1029F18619D5B6B2B45BD0A24E71908802E4F59B5F3F671ECDC4FBC49C26558855D9A94EDD7CFFFBF68EB3ADD0B24500923D18D7FA41F5DDBD944E81761EE77399FB44CC40951D90EC1D51E9326D2A78F6C912B36D2079DA5842F09E15908C701437A34BA5F0D87232F6D5DDF7AFB757D87AC7BE970D90FDC8F44F6C0302DF17E5DF06D65DC4E3AF7FBAFEA0B43D272CE59C4A3648B20092030E09E65A848613542247D62B57FF250724391C15EE7223D0F1E82272A4E37340921490C47088C984446BA3B002F66ED6A71CD54A0D49324092C2A1F5C3F77FFEBCCCD181B57F53C22A879490240324D524604AE1D50EC371FB24C0E1EA94AA9F930092088E96BA652456121C29216107E4F5DB6BE3ABF24E3235978A110DA592BAC7612D611F1459014911CCA532B561FD56CFD342B71D4C05CCBAC0910D901426B9EBFBCB12F76594844F22F7385624BBEF5F6FDFC41632F73E0B20094C32BB69E510766965160287132BCB6C3B0B20AFDF5E9B25245C4B111A1C09482B0C0E2B110E779B1C1066C136381A1C6B1220D70F524098E30E363F3381CE15F3892459475E6990EA091920CC710769A379FBA7DCD253641D1349872C1E210384D1B56A7024D02A66EB9FA005879FA0CA709200C238F2343812A8566D70EC4546A23BD18070BA5654A340021D2BF61395C24196FA8D0684CBB56A70F033C739B805D6DE6E4DD0CAEE5424FDC5EA5114205CAE15473E9B54EA151426048E833D3B4CFA14E56AC501F2F65A93EB4A5B78482ED2B902B92C3FB0F28B670330ED544467B5D080B0E4CB1B1C40FD8A7B2C231CDE4333984E4F414F20A2006132CF51A6304E65B6F3762638BC604C7B8065053172F04501C221E4D8606A3B2A8E6F2947BF796A831EB939B26B181D0B0684A3E24A29B48F885717DC9B46C9CC253952EF04596A158B4BBC2C423418D32219E291602F251810865128B8D238D58E7F6BD261459D98C2A0684BC22401C315CE118F845A91204038D270A1158E57735C09336D2755065CADFC6F3159FCC30F339E3DC610EF060DC8618010EFF32865BE6345C958B77BFAD57FFD09763818C16075B53A75053DC91F0C08B9F5406615629526F47D8092058D48A1DFC73E0FA837B668F3DEDDD330DCA4BA809321AB05EE333820C497DB94E05A81CDBB30D8C1F50E4424E7AD5BE4C003AD080810F2CA0953A8393D4104882232711C70E40463DA37C4092290150101425C315582F5C0B439774C85807AD5864801E320ABD5F764576640F4100608ED9A2B1123ED9A6660E070E5E58424A6DE5379E46C83CFEB238D85019E8C1710EA09A6A76178932AB8F3097BEEEF044A9625FD4B506F965341307DB0E4F29EF5FD07ADD47BC21373BC7D0501846CC5AEE491C9740AE1845AD2F46F2C1C52FB65BCA847EB775CB71FFBDABE0A08A939534A7DFF7AEB05926AC4092D873C11A11428080CADE7F1F311165EE46A8014501CC970B59FD601A19C1804A6D5621506F33E031CAE1AAC90202D9E3830324071A0266BC1FA2220D44A23D57A50B773065096A404C2BA8B0223371407FDB412AC2F021261BAE706711625C1588BE93B1C73067375F2F9B9A1ED08845A0C1893CB3F2903ED50F1CD3EBF6445D600213B5F576AE62A36B80DE9192A4802E0100186642820A9ED594088475691D623251CFB8EF0A6147DC001E1C80E46295040DCAC594010FEED62DF42662B7D8A41FDF70C708C41FBD3305C62E68100835656308A84E250B166132AF380D065AF58B338187088632B4C1582E7483C4B48B281510114DE6CD61220349383C252BBC8B4280602DF3B4103C782C5CB02466D50F8DCAC134028DD2B49C139D07FF72936E5DF41B1D90C1CC9C1A81A0A8F9B750A08957B05580846A96D6B650984C356D797D93A8223E8E89C58D94EA0203F0E34B66E9CEF1FC7CC7380D0B857424E2A0104B79CF2F696BD04C924564A06C656A1584BF71E0052A37B65AC8711801E063339357B5FBB5152D5F70F5E6D463CA087E1F3C26B36EDFBF8F8B83BCE6AED63A5F7AA5337D0BDD388AAD9571A1427923B880FD90091B6B4642D40E74C45BF5EDA4BB3E0823AA039AFB76E50AC0F27D3D8F91090B7D79F9646D9C0110A1480069619F5782980443572E5E506055CB2D301F3181092E5259C2332BC99874F6E119006054E5BA671E131202401BA34F7CA88692B8058176D182E382EA3C1A95B816F4DDCDF1110C2005D9C7B553B200D0A7208C740FD05908AE38F1A01D92814BB0314B47EF977D73DFFBFD6E714DB739D1734058424FE50C296973881D6E0620983C2AFACCF93A1FF1E8FEFBA53E3BB5DD71F9463D2DEEE79CCA24EAA496117474F0121893F242D2F99760C1610EF44A36740084DF34EEB3C09B27FF46417452A2BB9E30328D0DB5F8032CC232E5027074462801EE3627905CE08889B1331F58F1D59817A51C56324DB19F681BA0584CA2C49BE08A7440B5285B66668040920FB53692C2054192CDFE2BB0CB21A3FD900C929FDB4DF26DAD6603359A480480DD04B75B1D2AA553D5FA3F2884CB8F00C08518A57E20C7A6C162B670C528FCAA66D89B7CF80D53109275240A466B09A05016A44458F2D660F03DA68067C0708C91C88D40C560324402B2A79F435C1C6BF06C85E19D65C43AFB9664CF34E75D524523AADCEF5F341CE76DEC34CC2CD4DBACDED31A944EFC1CD20C96475EACA5990F84942415B6CE7A4584B160BB142D72DC178FEAF5B927104989BD176F32D98596CB0F626789002109395EDBC2324BC31221729D61AA45B50CECECED7764AC2BB6EF1C997197AB3EEA920B828124F0D90825CAC3565B783DCABFE4269F58E68C31B015B2FEBAD945B5438E31A4ED76251BA862473215A3F905910C993845B0AD2475806FD9E62552B0529C832665D4368DCD500014A7D3F69640E6C3087379C2FBC76B7589C6FF9B4D60F53D763A69CD9832294B223EC83EAD417AE8319445D3100EC2FA2C78C6C97FA3AE413BB8E6AD651AA05A15A461322D5C06793C46E1B3AFC2D50FC2B8F1B17AB0142274F6449490099D60D91094336ADF0D71A20223A303920AED542837B119D622BD10011D117D90039B62A3613567E704FD7A90D103A59469424029006CB4C0F52022275A97B0BD223D09D1E4DCA7857795C0D19DFA604446A166B92E675925C4ABB324AFAA8E8696A9831CD4BDDA0CD65C2B63451E89485621974ACE249DE37036DDB64E0C93FE0402B1DFA1C252026E2FFFECF9F97A17548F93CE1BAB3A86AD700C8463261779B59CD6B3A540A2052E3B528EA9D7CEBCA848D80506C980ABA772FB63330EF534D8A62BE7DF08ED0C3F5A2DB3529A08635617635AF6913C5EE2BB3B6C89C02412964EAB2A4002235A1412DEFA91B76D6F71FF69BBDEC854645FCC60D536F7EFD4CB1F253F2965B3B10FC74FD416965EE40C9FADB1A20536117B5CC851A10E9C1670324EBB870F0F1441BBEA21A3CDD934E72B3540304D61F5BB620731292BA266C3CF68764738969B9F0E093AC9D300ED69E12B7BC24BE493425480AEE5F0E8E23F2CDA58F8C0D101A254E554AEE0D5F232064D91DE19385526290122655534100FD4E86652ED6CADB342FE1049AF854AF08132E7C20812A6DAEE7F6FAFA3BF301152F80D8142851AA577AA03E9B724CBD52B50142C2166B70BF8FA75F2ED02102447AA0BED43389F3F3E22D2D8906272C84DA3370E74C8F809005B0858F8E89F2F30D102678A8E2CC934B3CC902F502969C40FB867A549A7CB70102ED84C0E7284E54349F3C018430501F0B0F6C1BFBE3C64AAABE7FF8F6D71F0FA11FA38645FAB29C50F948799E685DE1384F35BA583650273832DECE17F6FD254609B9853C69DFAE53EAFEDBD7DB8F986F52A41C1B2018C9AFBF43E55E4DE7F30E01210AD4A54E18CEEC26B4A71B767D7F8F051A9B726C8008066432C01F00527BA0EED96E6BADCAE330DC618EFE0F4D394ABE8D8B5E75D394481D7F586F685A75C2405DA49B05DC8FBEB3A791F7DD3DF6CC5C48BCB215408C2C8C8ED9AB1AF4706E2F01EAD4CE9DEA8EB5DC73C8112D4D3958277700C83E0E89BF4CE799BC1BAC8FCF31DE209310D6AA60037BD38EA54E931AA751C81EA1A8D1723EAE373A4E3C5A707B0A08511C226D872181758C0AEC0F6051EA7DD7F75794A3278562C7966107A1AEFB14B9F96E472D9B10588E2DFB0920048A34CA59D228191A23AC280B49608F8973621598EB7DA65508774FC370432D271F2CC7C993134090AEC892EC45EE7B80C40840658A0AEC81DF10FD18E5803AD3D068ABBD26BC99B3BD4EF4F504101B8754EA66310676EC97E148A4846ADEC1D736EE78D62D2F32F538767B6701214BF70A9E345C854529732315F68622F280D3A74039FE9E0A8E49DBB27823B38090BA5942162FDA0E356B6CFEBE5DBE6E6DDF1B840B1677AA5337906FE65072EC3799DDAAC56A715B92B90FCF0252A39B35B18A41413651701FF44DACE2A6788F74F00CAF70F201671910A27DEA7B1964318F53F92FCCB206058144C17DD037C37588F70DC2F8145BD1DDD3305C5267B7962AB30808F14891B451738DF52C43081EE1119361C7D5CA3E68846A6886B863A98AC964B70808B19B658A4BD6280420D3578253B7A8790021B159082454ABBD43BEB9F02CF96462B005312F10076359AD08C235084EDD06C52B850122C87A385D4E32E0AE5A909AAC08029013AB12B226CB1BAF940608D15E2102EBE18A48B22BD30F086DB09ECD8AF8961804745C7026650196241D1CD0AED547812BA1A93E072A27C552262F20C4C17AF65884748E03B1D96A1ADC4BBF91CB69A940F72A999BE50584C1CDCA66458E87A5A098617D4C2B3A75EB1BAEA93622F9BE83F83BBB150601421CAC67B722731DE18D1960BD179C2E86159BF7A9C8F88DB3F2320061B022C9771C869C684230C76144169C2EE6D4A498B205A5774F9AC1BDB71F64411852BE5681525ED936093283826CD41CC771379A7BD123B6F0C62837C5BB120374D72EEEADCB604038AC48AAC9C3854443903B4414AF14B9D2B701021C6618325A495C2D40BD83826CAA7825A5050576F1EC63925D2C5116C45A91B7D724D7B54D7A82DDD50A4832045915D386987825451E3F060CF7AEE0209DFD14CF2017CB29C4ABBE37F7AA53FE58970D20F3F8C14176E8646403245A85D807D7604098AC08ABAB8504C4F51EEA9CACBD5BB77AC94B298050EE308D46E2B000D681D57C0A0508C0A7C7C8816D34208A19C6D42D6A4D9656EF4E6E44127EE9A9EBC4001715D3EFF87712C80F05884DFBBEBDFEA895322324E58F0D1257C99898E1A8A141E9E2D13D7DD55FA841BFB7674725E860AACE9118877007E8680B327676FC216173FDC76E364F60893CA4017300B601D5D423D5CEB85850189233B1554AA227680BC218B0273FB694720163CCB50AB11AC3F93E935B8DAE72AAF82D0A1046572B392453ABF26AEA06E1BA3078B315EE3369DF12634512EEA58906643FB27C8E38476AA997C9B6559A3A625C19A2E03E385D9C56EDE15F93624552598FA818642A56C62C07C9D2F87D80791E7BFFC759DF7FD07157460707F670F54DF36464CA3CBA92A9CFC68AB620AEC58CE6373AB3759281895C3C48B0803178C63E5AB3080B60CA60FA6B98D0B57295210384D1D532758DB2242B6B89A214353AB8CFD0E17E2DF43F61FB9A2783B9F6F1E881D2DFB2D327C800B101FBCFBF5DE86130F108C70F0D0970B15DD082C5E306A2E2954201316D27B0A2213A82EEFB908FCC3D4B0A8885846702D1D51D25A8C0E5DA5156C5290F30139665548C551AF77E1248320F22E48018E131C62328772B1090A9FE4467A03C33F74503E204B51F14634EC49F653675409EC4828C2328AF8F1A94028E0064B45C66B65C75EA4BCC49ED33236E158050BB5C12C0200FD2677D72FA65F127A3BBEFA25086DC3D8955B12E9856EFBE7FBDBDA272792494838AC59E2B1E2D578EF6B3B858A3E9E50DDA47A1AE41C200C801A435DEFF41A5680E167BF5B3523F2AAD5F2E25EA3A03C4BFE65BD8BBE9A9EAB9560E2B20098276DBB63593CC9C593B70C1BABEBFAFEDE6DA144A28F91BEC80A482C49AE899AB95130112ECFA49568A98BAB9B9A19832AC55797CDC619607C57EF7F8FD24804076D71135EC642E23E3D288E87431914CD88A31838F1A868BC8E5376BF53332544AEB5DD7755F4236AA51353A0920A6B289675FC7F9928C809C5815C9BE3654A14628E837CB41AB9074E04906480E48CCE244DDA99DD2CA9CC422E157EC32F8BDAB6AE488BDFD975EFE5A3F3C697DC5E98A2505240324E6934629E574EA8B9A884C6B1E6B7162CB8F8188558EC901C9040946F069DE11BC2F9D79D910B57CA3D6D22D55260B200D924977080544E2210D00A2C821C906C80849DF73EC4604C852C823C20029C0A5F2761CE55295AC803848EC4EBD7C5911AFC0591F100408F3AA035631CE144E72EA497640B60E09E56817AB8185BA558BCDA690AD08405C0B0B0B0A63F5D1BE4FD1891415A90D8EBD4C82567DCFC9511420A68219968650E817BA0C0980543E30A136D9B90E1507C8D682F7DC80541677CC0F5411BB124502B225487203C2BCFB136D59A95FC49EA52516900D05EF24D9168C426DCA9D455A11D180B84E673E5208A35B94EF9000E20EC3363B15A1DB822B0DCC97B35A7D7F19BA5FA708402AB72620401C00676767E75A0FE766979E91CBDC5273E8D5C8047BF529070AFEB21056A41840C654F0F3365E59AB4AE3BAF679CFC3F22F74A125E8200821DB00E22417FE364836D3628B03A4726B12DEE5A76F802CD2D6DCAB316D1BE8661509886BEC7E1434B75C858EB2148A28B20C68566C73EE95EBAD4037AB68409A35996114B0B66BA3EE951356909B553C20D34CD7A6173DEE0501B9B7AFF29973AF6587C86874C9BCA515F6400DCBB563440EC9606D657270498E219386D5589063616C323E01FAD70D10F87C48B580B8F80478CA7ACCA02DE9DD96C182F406204EABD6C55A924FC2B3B9205DC4F30CB0E381F7A5F0D45140A9D04C9FA96AD51664AE2F92DC69914909A0C1670344DDF80E3DDF9C0539D6D90928E4F75A64E24341027453B7AD4E128EFD02B4B49BB420AB5625EE06DB5C5C8CDF8502D2D2BCC31BE861739B73B17C5AECB911CAF77ACEBF83027453C14D2D733FED916D4E1472686649B084049E1612DEBB2439BA83A4CC903990E66205887CBC3949AB77F62298AEBB08789DFFD100BFDA5586EB6E41FEC6A2BE803AC0A1B95828593F9F566F5E7557A9E586069AC15A4C56141E7F2D74E3CE5C9DA0FAEE1EBA89ECB89C06081290C5607FBFA1C9FEDD581BFB5F728B33DE9BA1F6579941D3966BCD1DADA4ABFB73BDDD4A69A92BA65FF6D3B87B444CFDFBFE2174F7E09C6C1A208480F88A9AEE0A74CF9ADD81BEF7BAAEB74A20E1D625D7065F9DB9FF0ECD42C5D6E3FF52BEF6470B7511FF0000000049454E44AE426082, '1', '2023-11-11 18:08:52', '1', '2023-12-04 16:51:49', b'0');

-- ----------------------------
-- Table structure for infra_job
-- ----------------------------
DROP TABLE IF EXISTS `infra_job`;
CREATE TABLE `infra_job`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务名称',
  `status` tinyint NOT NULL COMMENT '任务状态',
  `handler_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '处理器的名字',
  `handler_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理器的参数',
  `cron_expression` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'CRON 表达式',
  `retry_count` int NOT NULL DEFAULT 0 COMMENT '重试次数',
  `retry_interval` int NOT NULL DEFAULT 0 COMMENT '重试间隔',
  `monitor_timeout` int NOT NULL DEFAULT 0 COMMENT '监控超时时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '定时任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_job
-- ----------------------------
INSERT INTO `infra_job` VALUES (20, '同步点赞缓存到MySQL', 1, 'FeedbackLikeJob', '', '0 0 * * * ?', 0, 0, 0, '1', '2023-10-30 21:50:01', '1', '2023-10-31 13:02:35', b'0');

-- ----------------------------
-- Table structure for infra_job_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_job_log`;
CREATE TABLE `infra_job_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `job_id` bigint NOT NULL COMMENT '任务编号',
  `handler_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '处理器的名字',
  `handler_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理器的参数',
  `execute_index` tinyint NOT NULL DEFAULT 1 COMMENT '第几次执行',
  `begin_time` datetime NOT NULL COMMENT '开始执行时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束执行时间',
  `duration` int NULL DEFAULT NULL COMMENT '执行时长',
  `status` tinyint NOT NULL COMMENT '任务状态',
  `result` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果数据',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725921809253900290 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '定时任务日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_data`;
CREATE TABLE `system_dict_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `sort` int NOT NULL DEFAULT 0 COMMENT '字典排序',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `color_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '颜色类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'css 样式',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100909 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_dict_data
-- ----------------------------
INSERT INTO `system_dict_data` VALUES (8, 1, '正常', '1', 'infra_job_status', 0, 'success', '', '正常状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:33:38', b'0');
INSERT INTO `system_dict_data` VALUES (9, 2, '暂停', '2', 'infra_job_status', 0, 'danger', '', '停用状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:33:45', b'0');
INSERT INTO `system_dict_data` VALUES (12, 1, '系统内置', '1', 'infra_config_type', 0, 'danger', '', '参数类型 - 系统内置', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:06:02', b'0');
INSERT INTO `system_dict_data` VALUES (13, 2, '自定义', '2', 'infra_config_type', 0, 'primary', '', '参数类型 - 自定义', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:06:07', b'0');
INSERT INTO `system_dict_data` VALUES (16, 0, '其它', '0', 'system_operate_type', 0, 'default', '', '其它操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:32:46', b'0');
INSERT INTO `system_dict_data` VALUES (17, 1, '查询', '1', 'system_operate_type', 0, 'info', '', '查询操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:16', b'0');
INSERT INTO `system_dict_data` VALUES (18, 2, '新增', '2', 'system_operate_type', 0, 'primary', '', '新增操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:13', b'0');
INSERT INTO `system_dict_data` VALUES (19, 3, '修改', '3', 'system_operate_type', 0, 'warning', '', '修改操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:22', b'0');
INSERT INTO `system_dict_data` VALUES (20, 4, '删除', '4', 'system_operate_type', 0, 'danger', '', '删除操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:27', b'0');
INSERT INTO `system_dict_data` VALUES (22, 5, '导出', '5', 'system_operate_type', 0, 'default', '', '导出操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:32', b'0');
INSERT INTO `system_dict_data` VALUES (23, 6, '导入', '6', 'system_operate_type', 0, 'default', '', '导入操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:35', b'0');
INSERT INTO `system_dict_data` VALUES (27, 1, '开启', '0', 'common_status', 0, 'primary', '', '开启状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 08:00:39', b'0');
INSERT INTO `system_dict_data` VALUES (28, 2, '关闭', '1', 'common_status', 0, 'info', '', '关闭状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 08:00:44', b'0');
INSERT INTO `system_dict_data` VALUES (29, 1, '目录', '1', 'system_menu_type', 0, '', '', '目录', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:45', b'0');
INSERT INTO `system_dict_data` VALUES (30, 2, '菜单', '2', 'system_menu_type', 0, '', '', '菜单', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:41', b'0');
INSERT INTO `system_dict_data` VALUES (31, 3, '按钮', '3', 'system_menu_type', 0, '', '', '按钮', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:39', b'0');
INSERT INTO `system_dict_data` VALUES (34, 1, '全部数据权限', '1', 'system_data_scope', 0, '', '', '全部数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:17', b'0');
INSERT INTO `system_dict_data` VALUES (35, 2, '指定部门数据权限', '2', 'system_data_scope', 0, '', '', '指定部门数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:18', b'0');
INSERT INTO `system_dict_data` VALUES (36, 3, '本部门数据权限', '3', 'system_data_scope', 0, '', '', '本部门数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:16', b'0');
INSERT INTO `system_dict_data` VALUES (37, 4, '本部门及以下数据权限', '4', 'system_data_scope', 0, '', '', '本部门及以下数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:21', b'0');
INSERT INTO `system_dict_data` VALUES (38, 5, '仅本人数据权限', '5', 'system_data_scope', 0, '', '', '仅本人数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:23', b'0');
INSERT INTO `system_dict_data` VALUES (39, 0, '成功', '0', 'system_login_result', 0, 'success', '', '登陆结果 - 成功', '', '2021-01-18 06:17:36', '1', '2022-02-16 13:23:49', b'0');
INSERT INTO `system_dict_data` VALUES (40, 10, '账号或密码不正确', '10', 'system_login_result', 0, 'primary', '', '登陆结果 - 账号或密码不正确', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:27', b'0');
INSERT INTO `system_dict_data` VALUES (41, 20, '用户被禁用', '20', 'system_login_result', 0, 'warning', '', '登陆结果 - 用户被禁用', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:23:57', b'0');
INSERT INTO `system_dict_data` VALUES (42, 30, '验证码不存在', '30', 'system_login_result', 0, 'info', '', '登陆结果 - 验证码不存在', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:07', b'0');
INSERT INTO `system_dict_data` VALUES (43, 31, '验证码不正确', '31', 'system_login_result', 0, 'info', '', '登陆结果 - 验证码不正确', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:11', b'0');
INSERT INTO `system_dict_data` VALUES (44, 100, '未知异常', '100', 'system_login_result', 0, 'danger', '', '登陆结果 - 未知异常', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:23', b'0');
INSERT INTO `system_dict_data` VALUES (45, 1, '是', 'true', 'infra_boolean_string', 0, 'danger', '', 'Boolean 是否类型 - 是', '', '2021-01-19 03:20:55', '1', '2022-03-15 23:01:45', b'0');
INSERT INTO `system_dict_data` VALUES (46, 1, '否', 'false', 'infra_boolean_string', 0, 'info', '', 'Boolean 是否类型 - 否', '', '2021-01-19 03:20:55', '1', '2022-03-15 23:09:45', b'0');
INSERT INTO `system_dict_data` VALUES (47, 1, '永不超时', '1', 'infra_redis_timeout_type', 0, 'primary', '', 'Redis 未设置超时的情况', '', '2021-01-26 00:53:17', '1', '2022-02-16 19:03:35', b'0');
INSERT INTO `system_dict_data` VALUES (48, 1, '动态超时', '2', 'infra_redis_timeout_type', 0, 'info', '', '程序里动态传入超时时间，无法固定', '', '2021-01-26 00:55:00', '1', '2022-02-16 19:03:41', b'0');
INSERT INTO `system_dict_data` VALUES (49, 3, '固定超时', '3', 'infra_redis_timeout_type', 0, 'success', '', 'Redis 设置了过期时间', '', '2021-01-26 00:55:26', '1', '2022-02-16 19:03:45', b'0');
INSERT INTO `system_dict_data` VALUES (50, 1, '单表（增删改查）', '1', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:09:06', '', '2022-03-10 16:33:15', b'0');
INSERT INTO `system_dict_data` VALUES (51, 2, '树表（增删改查）', '2', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:14:46', '', '2022-03-10 16:33:19', b'0');
INSERT INTO `system_dict_data` VALUES (53, 0, '初始化中', '0', 'infra_job_status', 0, 'primary', '', NULL, '', '2021-02-07 07:46:49', '1', '2022-02-16 19:33:29', b'0');
INSERT INTO `system_dict_data` VALUES (57, 0, '运行中', '0', 'infra_job_log_status', 0, 'primary', '', 'RUNNING', '', '2021-02-08 10:04:24', '1', '2022-02-16 19:07:48', b'0');
INSERT INTO `system_dict_data` VALUES (58, 1, '成功', '1', 'infra_job_log_status', 0, 'success', '', NULL, '', '2021-02-08 10:06:57', '1', '2022-02-16 19:07:52', b'0');
INSERT INTO `system_dict_data` VALUES (59, 2, '失败', '2', 'infra_job_log_status', 0, 'warning', '', '失败', '', '2021-02-08 10:07:38', '1', '2022-02-16 19:07:56', b'0');
INSERT INTO `system_dict_data` VALUES (60, 1, '普通用户', '2', 'user_type', 0, 'primary', '', NULL, '', '2021-02-26 00:16:27', '1', '2023-11-29 17:30:35', b'0');
INSERT INTO `system_dict_data` VALUES (61, 2, '管理员', '1', 'user_type', 0, 'success', '', NULL, '', '2021-02-26 00:16:34', '1', '2023-11-29 17:30:29', b'0');
INSERT INTO `system_dict_data` VALUES (62, 0, '未处理', '0', 'infra_api_error_log_process_status', 0, 'primary', '', NULL, '', '2021-02-26 07:07:19', '1', '2022-02-16 20:14:17', b'0');
INSERT INTO `system_dict_data` VALUES (63, 1, '已处理', '1', 'infra_api_error_log_process_status', 0, 'success', '', NULL, '', '2021-02-26 07:07:26', '1', '2022-02-16 20:14:08', b'0');
INSERT INTO `system_dict_data` VALUES (64, 2, '已忽略', '2', 'infra_api_error_log_process_status', 0, 'danger', '', NULL, '', '2021-02-26 07:07:34', '1', '2022-02-16 20:14:14', b'0');
INSERT INTO `system_dict_data` VALUES (80, 100, '账号登录', '100', 'system_login_type', 0, 'primary', '', '账号登录', '1', '2021-10-06 00:52:02', '1', '2022-02-16 13:11:34', b'0');
INSERT INTO `system_dict_data` VALUES (81, 101, '社交登录', '101', 'system_login_type', 0, 'info', '', '社交登录', '1', '2021-10-06 00:52:17', '1', '2022-02-16 13:11:40', b'0');
INSERT INTO `system_dict_data` VALUES (83, 200, '主动登出', '200', 'system_login_type', 0, 'primary', '', '主动登出', '1', '2021-10-06 00:52:58', '1', '2022-02-16 13:11:49', b'0');
INSERT INTO `system_dict_data` VALUES (85, 202, '强制登出', '202', 'system_login_type', 0, 'danger', '', '强制退出', '1', '2021-10-06 00:53:41', '1', '2022-02-16 13:11:57', b'0');
INSERT INTO `system_dict_data` VALUES (1145, 1, '管理后台', '1', 'infra_codegen_scene', 0, '', '', '代码生成的场景枚举 - 管理后台', '1', '2022-02-02 13:15:06', '1', '2022-03-10 16:32:59', b'0');
INSERT INTO `system_dict_data` VALUES (1146, 2, '用户 APP', '2', 'infra_codegen_scene', 0, '', '', '代码生成的场景枚举 - 用户 APP', '1', '2022-02-02 13:15:19', '1', '2022-03-10 16:33:03', b'0');
INSERT INTO `system_dict_data` VALUES (1150, 1, '数据库', '1', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:28', '1', '2022-03-15 00:25:28', b'0');
INSERT INTO `system_dict_data` VALUES (1151, 10, '本地磁盘', '10', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:41', '1', '2022-03-15 00:25:56', b'0');
INSERT INTO `system_dict_data` VALUES (1152, 11, 'FTP 服务器', '11', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:06', '1', '2022-03-15 00:26:10', b'0');
INSERT INTO `system_dict_data` VALUES (1153, 12, 'SFTP 服务器', '12', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:22', '1', '2022-03-15 00:26:22', b'0');
INSERT INTO `system_dict_data` VALUES (1154, 20, 'S3 对象存储', '20', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:31', '1', '2022-03-15 00:26:45', b'0');
INSERT INTO `system_dict_data` VALUES (1155, 103, '短信登录', '103', 'system_login_type', 0, 'default', '', NULL, '1', '2022-05-09 23:57:58', '1', '2022-05-09 23:58:09', b'0');
INSERT INTO `system_dict_data` VALUES (1194, 10, '微信小程序', '10', 'terminal', 0, 'default', '', '终端 - 微信小程序', '1', '2022-12-10 10:51:11', '1', '2022-12-10 10:51:57', b'0');
INSERT INTO `system_dict_data` VALUES (1195, 20, 'H5 网页', '20', 'terminal', 0, 'default', '', '终端 - H5 网页', '1', '2022-12-10 10:51:30', '1', '2022-12-10 10:51:59', b'0');
INSERT INTO `system_dict_data` VALUES (1196, 11, '微信公众号', '11', 'terminal', 0, 'default', '', '终端 - 微信公众号', '1', '2022-12-10 10:54:16', '1', '2022-12-10 10:52:01', b'0');
INSERT INTO `system_dict_data` VALUES (1197, 31, '苹果 App', '31', 'terminal', 0, 'default', '', '终端 - 苹果 App', '1', '2022-12-10 10:54:42', '1', '2022-12-10 10:52:18', b'0');
INSERT INTO `system_dict_data` VALUES (1198, 32, '安卓 App', '32', 'terminal', 0, 'default', '', '终端 - 安卓 App', '1', '2022-12-10 10:55:02', '1', '2022-12-10 10:59:17', b'0');
INSERT INTO `system_dict_data` VALUES (1223, 0, '初始化', '0', 'system_mail_send_status', 0, 'primary', '', '邮件发送状态 - 初始化\n', '1', '2023-01-26 09:53:49', '1', '2023-01-26 16:36:14', b'0');
INSERT INTO `system_dict_data` VALUES (1224, 10, '发送成功', '10', 'system_mail_send_status', 0, 'success', '', '邮件发送状态 - 发送成功', '1', '2023-01-26 09:54:28', '1', '2023-01-26 16:36:22', b'0');
INSERT INTO `system_dict_data` VALUES (1225, 20, '发送失败', '20', 'system_mail_send_status', 0, 'danger', '', '邮件发送状态 - 发送失败', '1', '2023-01-26 09:54:50', '1', '2023-01-26 16:36:26', b'0');
INSERT INTO `system_dict_data` VALUES (1226, 30, '不发送', '30', 'system_mail_send_status', 0, 'info', '', '邮件发送状态 -  不发送', '1', '2023-01-26 09:55:06', '1', '2023-01-26 16:36:36', b'0');
INSERT INTO `system_dict_data` VALUES (1231, 10, 'Vue2 Element UI 标准模版', '10', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:03:55', '1', '2023-04-13 00:03:55', b'0');
INSERT INTO `system_dict_data` VALUES (1232, 20, 'Vue3 Element Plus 标准模版', '20', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:04:08', '1', '2023-04-13 00:04:08', b'0');
INSERT INTO `system_dict_data` VALUES (1233, 21, 'Vue3 Element Plus Schema 模版', '21', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:04:26', '1', '2023-04-13 00:04:26', b'0');
INSERT INTO `system_dict_data` VALUES (1234, 30, 'Vue3 vben 模版', '30', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:04:26', '1', '2023-04-13 00:04:26', b'0');
INSERT INTO `system_dict_data` VALUES (1358, 1, '待回复', '0', 'harbor_reply_state', 0, 'primary', '', '', '1', '2023-10-28 17:56:11', '1', '2023-10-28 19:05:54', b'0');
INSERT INTO `system_dict_data` VALUES (1359, 2, '已回复', '1', 'harbor_reply_state', 0, 'success', '', '', '1', '2023-10-28 17:56:23', '1', '2023-10-28 18:41:02', b'0');
INSERT INTO `system_dict_data` VALUES (100855, 1, '同意', '1', 'system_invite_status', 0, '', '', '', '1', '2023-11-26 10:44:51', '1', '2023-11-26 10:56:35', b'0');
INSERT INTO `system_dict_data` VALUES (100857, 2, '拒绝', '2', 'system_invite_status', 0, '', '', '', '1', '2023-11-26 10:45:12', '1', '2023-11-26 10:56:36', b'0');
INSERT INTO `system_dict_data` VALUES (100859, 3, '待回复', '3', 'system_invite_status', 0, '', '', '', '1', '2023-11-26 10:45:27', '1', '2023-11-26 10:56:37', b'0');
INSERT INTO `system_dict_data` VALUES (100901, 1, '超级管理员', 'super_admin', 'system_role_code', 0, '', '', '', '1', '2023-11-27 14:39:08', '1', '2023-11-27 14:39:08', b'0');
INSERT INTO `system_dict_data` VALUES (100904, 2, '系统管理员', 'admin', 'system_role_code', 0, '', '', '', '1', '2023-11-27 14:40:06', '1', '2023-11-27 14:44:22', b'0');
INSERT INTO `system_dict_data` VALUES (100906, 3, '超级租户管理员', 'super_tenant_admin', 'system_role_code', 0, '', '', '', '1', '2023-11-27 14:40:20', '1', '2023-11-27 14:44:15', b'0');
INSERT INTO `system_dict_data` VALUES (100908, 4, '租户管理员', 'tenant_admin', 'system_role_code', 0, '', '', '', '1', '2023-11-27 14:40:26', '1', '2023-11-27 14:44:09', b'0');

-- ----------------------------
-- Table structure for system_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_type`;
CREATE TABLE `system_dict_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典名称',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `deleted_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100900 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_dict_type
-- ----------------------------
INSERT INTO `system_dict_type` VALUES (6, '参数类型', 'infra_config_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:36:54', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (9, '操作类型', 'system_operate_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:32:21', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (10, '系统状态', 'common_status', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:21:28', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (11, 'Boolean 是否类型', 'infra_boolean_string', 0, 'boolean 转是否', '', '2021-01-19 03:20:08', '', '2022-02-01 16:37:10', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (104, '登陆结果', 'system_login_result', 0, '登陆结果', '', '2021-01-18 06:17:11', '', '2022-02-01 16:36:00', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (105, 'Redis 超时类型', 'infra_redis_timeout_type', 0, 'RedisKeyDefine.TimeoutTypeEnum', '', '2021-01-26 00:52:50', '', '2022-02-01 16:50:29', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (106, '代码生成模板类型', 'infra_codegen_template_type', 0, NULL, '', '2021-02-05 07:08:06', '1', '2022-05-16 20:26:50', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (107, '定时任务状态', 'infra_job_status', 0, NULL, '', '2021-02-07 07:44:16', '', '2022-02-01 16:51:11', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (108, '定时任务日志状态', 'infra_job_log_status', 0, NULL, '', '2021-02-08 10:03:51', '', '2022-02-01 16:50:43', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (109, '用户类型', 'user_type', 0, NULL, '', '2021-02-26 00:15:51', '', '2021-02-26 00:15:51', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (110, 'API 异常数据的处理状态', 'infra_api_error_log_process_status', 0, NULL, '', '2021-02-26 07:07:01', '', '2022-02-01 16:50:53', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (116, '登陆日志的类型', 'system_login_type', 0, '登陆日志的类型', '1', '2021-10-06 00:50:46', '1', '2022-02-01 16:35:56', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (144, '代码生成的场景枚举', 'infra_codegen_scene', 0, '代码生成的场景枚举', '1', '2022-02-02 13:14:45', '1', '2022-03-10 16:33:46', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (146, '文件存储器', 'infra_file_storage', 0, '文件存储器', '1', '2022-03-15 00:24:38', '1', '2022-03-15 00:24:38', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (160, '终端', 'terminal', 0, '终端', '1', '2022-12-10 10:50:50', '1', '2022-12-10 10:53:11', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (166, '邮件发送状态', 'system_mail_send_status', 0, '邮件发送状态', '1', '2023-01-26 09:53:13', '1', '2023-01-26 09:53:13', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (168, '代码生成的前端类型', 'infra_codegen_front_type', 0, '', '1', '2023-04-12 23:57:52', '1', '2023-04-12 23:57:52', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (180, '反馈回复状态', 'harbor_reply_state', 0, '', '1', '2023-10-28 17:55:50', '1', '2023-10-28 17:55:50', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (100853, '邀请状态', 'system_invite_status', 0, '', '1', '2023-11-26 10:44:21', '1', '2023-11-26 10:56:31', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (100899, '角色标识', 'system_role_code', 0, '', '1', '2023-11-27 14:38:32', '1', '2023-11-27 14:38:32', b'0', '1970-01-01 00:00:00');

-- ----------------------------
-- Table structure for system_invite_link
-- ----------------------------
DROP TABLE IF EXISTS `system_invite_link`;
CREATE TABLE `system_invite_link`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '租户编号',
  `code` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接标识',
  `status` tinyint NULL DEFAULT NULL COMMENT '链接状态',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `type` tinyint NOT NULL COMMENT '链接类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101217 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '链接邀请' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_invite_link
-- ----------------------------

-- ----------------------------
-- Table structure for system_invite_user
-- ----------------------------
DROP TABLE IF EXISTS `system_invite_user`;
CREATE TABLE `system_invite_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '产品编号',
  `inviter_user_id` bigint NOT NULL COMMENT '邀请人的用户id',
  `invitee_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '受邀人用户id',
  `status` tinyint NOT NULL COMMENT '受邀状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100943 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '团队邀请' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_invite_user
-- ----------------------------

-- ----------------------------
-- Table structure for system_login_log
-- ----------------------------
DROP TABLE IF EXISTS `system_login_log`;
CREATE TABLE `system_login_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `log_type` bigint NOT NULL COMMENT '日志类型',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `result` tinyint NOT NULL COMMENT '登陆结果',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725807246101483522 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_mail_account
-- ----------------------------
DROP TABLE IF EXISTS `system_mail_account`;
CREATE TABLE `system_mail_account`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SMTP 服务器域名',
  `port` int NOT NULL COMMENT 'SMTP 服务器端口',
  `ssl_enable` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否开启 SSL',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '邮箱账号表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_mail_account
-- ----------------------------
INSERT INTO `system_mail_account` VALUES (2, 'feedbackharbor@163.com', 'feedbackharbor@163.com', 'OQLMXVTGAAUMUFCB', 'smtp.163.com', 465, b'1', '1', '2023-01-26 01:26:03', '1', '2023-11-28 16:43:24', b'0');

-- ----------------------------
-- Table structure for system_mail_captcha
-- ----------------------------
DROP TABLE IF EXISTS `system_mail_captcha`;
CREATE TABLE `system_mail_captcha`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `mail` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `captcha` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '验证码',
  `create_ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建 IP',
  `scene` tinyint NOT NULL COMMENT '发送场景',
  `today_index` tinyint NOT NULL COMMENT '今日发送的第几条',
  `used` tinyint NOT NULL COMMENT '是否使用',
  `used_time` datetime NULL DEFAULT NULL COMMENT '使用时间',
  `used_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用 IP',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101194 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '邮箱验证码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_mail_captcha
-- ----------------------------
INSERT INTO `system_mail_captcha` VALUES (101153, '619215399@qq.com', '9999', '0:0:0:0:0:0:0:1', 1, 1, 0, NULL, NULL, NULL, '2023-11-29 23:55:32', NULL, '2023-11-29 23:55:32', b'0', 0);
INSERT INTO `system_mail_captcha` VALUES (101156, '619215399@qq.com', '9999', '0:0:0:0:0:0:0:1', 1, 1, 1, '2023-11-30 00:01:04', '0:0:0:0:0:0:0:1', NULL, '2023-11-30 00:00:28', NULL, '2023-11-30 00:01:04', b'0', 0);
INSERT INTO `system_mail_captcha` VALUES (101158, '619215399@qq.com', '109572', '0:0:0:0:0:0:0:1', 1, 2, 1, '2023-11-30 00:03:36', '0:0:0:0:0:0:0:1', NULL, '2023-11-30 00:03:16', NULL, '2023-11-30 00:03:36', b'0', 0);
INSERT INTO `system_mail_captcha` VALUES (101180, '619215399@qq.com', '773044', '0:0:0:0:0:0:0:1', 2, 3, 0, NULL, NULL, NULL, '2023-11-30 15:55:34', NULL, '2023-11-30 15:55:34', b'0', 0);
INSERT INTO `system_mail_captcha` VALUES (101182, '619215399@qq.com', '785542', '0:0:0:0:0:0:0:1', 2, 4, 0, NULL, NULL, NULL, '2023-11-30 15:56:37', NULL, '2023-11-30 15:56:37', b'0', 0);
INSERT INTO `system_mail_captcha` VALUES (101185, '619215399@qq.com', '870935', '0:0:0:0:0:0:0:1', 2, 5, 0, NULL, NULL, NULL, '2023-11-30 16:27:32', NULL, '2023-11-30 16:27:32', b'0', 0);
INSERT INTO `system_mail_captcha` VALUES (101192, '619215399@qq.com', '160512', '0:0:0:0:0:0:0:1', 2, 6, 0, NULL, NULL, NULL, '2023-11-30 16:30:15', NULL, '2023-11-30 16:30:15', b'0', 0);
INSERT INTO `system_mail_captcha` VALUES (101194, '619215399@qq.com', '416564', '0:0:0:0:0:0:0:1', 2, 7, 1, '2023-11-30 16:33:36', '0:0:0:0:0:0:0:1', NULL, '2023-11-30 16:33:18', NULL, '2023-11-30 16:33:36', b'0', 0);

-- ----------------------------
-- Table structure for system_mail_log
-- ----------------------------
DROP TABLE IF EXISTS `system_mail_log`;
CREATE TABLE `system_mail_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户编号',
  `to_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接收邮箱地址',
  `account_id` bigint NOT NULL COMMENT '邮箱账号编号',
  `from_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发送邮箱地址',
  `template_id` bigint NOT NULL COMMENT '模板编号',
  `template_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
  `template_nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模版发送人名称',
  `template_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件标题',
  `template_content` varchar(10240) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件内容',
  `template_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件参数',
  `send_status` tinyint NOT NULL DEFAULT 0 COMMENT '发送状态',
  `send_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `send_message_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发送返回的消息 ID',
  `send_exception` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发送异常',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101217 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '邮件日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_mail_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_mail_template
-- ----------------------------
DROP TABLE IF EXISTS `system_mail_template`;
CREATE TABLE `system_mail_template`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板名称',
  `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
  `account_id` bigint NOT NULL COMMENT '发送的邮箱账号编号',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发送人名称',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板标题',
  `content` varchar(10240) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板内容',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数数组',
  `status` tinyint NOT NULL COMMENT '开启状态',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101205 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '邮件模版表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_mail_template
-- ----------------------------
INSERT INTO `system_mail_template` VALUES (14, '注册验证码', 'register_captcha', 2, '反馈港', '注册验证码', '<p style=\"text-align: center;\"><img src=\"http://127.0.0.1:48080/admin-api/infra/file/4/get/a4b34998498c5fc7c7cf17405fea27c4f4d70b30336d2d20ea59ce5ff7fa88c8.png\" alt=\"image\" data-href=\"http://127.0.0.1:48080/admin-api/infra/file/4/get/a4b34998498c5fc7c7cf17405fea27c4f4d70b30336d2d20ea59ce5ff7fa88c8.png\" style=\"width: 40.88px;height: 40.88px;\"><span style=\"font-family: 微软雅黑;\"> </span><span style=\"font-family: 微软雅黑;\"><strong>反馈港</strong></span></p><p style=\"text-align: center;\"><span style=\"font-family: 微软雅黑;\">你正在注册</span><span style=\"font-family: 微软雅黑;\"><strong>反馈港</strong></span><span style=\"font-family: 微软雅黑;\">账号，你的验证码为：</span><span style=\"font-family: 微软雅黑;\"><code><strong>{captcha}</strong></code></span></p><p style=\"text-align: center;\"><span style=\"font-family: 微软雅黑;\">如非本人操作请忽略!</span></p><p style=\"text-align: left;\"><br></p><p><br></p>', '[\"captcha\"]', 0, NULL, '1', '2023-01-26 01:27:40', '1', '2023-11-30 15:50:05', b'0');
INSERT INTO `system_mail_template` VALUES (101174, '重置密码验证码', 'reset_passwd_captcha', 2, '反馈港', '重置密码', '<p style=\"text-align: center;\"><img src=\"http://127.0.0.1:48080/admin-api/infra/file/4/get/a4b34998498c5fc7c7cf17405fea27c4f4d70b30336d2d20ea59ce5ff7fa88c8.png\" alt=\"image\" data-href=\"http://127.0.0.1:48080/admin-api/infra/file/4/get/a4b34998498c5fc7c7cf17405fea27c4f4d70b30336d2d20ea59ce5ff7fa88c8.png\" style=\"width: 40.88px;height: 40.88px;\"><span style=\"font-family: 微软雅黑;\"> </span><span style=\"font-family: 微软雅黑;\"><strong>反馈港</strong></span></p><p style=\"text-align: center;\"><span style=\"font-family: 微软雅黑;\">你正在重置</span><span style=\"font-family: 微软雅黑;\"><strong>反馈港</strong></span><span style=\"font-family: 微软雅黑;\">账号的密码，你的验证码为：</span><span style=\"font-family: 微软雅黑;\"><code><strong>{captcha}</strong></code></span></p><p style=\"text-align: center;\"><span style=\"font-family: 微软雅黑;\">如非本人操作请忽略！ 不要泄露验证码给他人！</span></p>', '[\"captcha\"]', 0, '', '1', '2023-11-30 15:49:28', '1', '2023-11-30 15:51:41', b'0');
INSERT INTO `system_mail_template` VALUES (101204, '邀请加入团队', 'invite_join_team', 2, '反馈港', '邀请加入团队', '<p style=\"text-align: center;\"><img src=\"http://127.0.0.1:48080/admin-api/infra/file/4/get/a4b34998498c5fc7c7cf17405fea27c4f4d70b30336d2d20ea59ce5ff7fa88c8.png\" alt=\"image\" data-href=\"http://127.0.0.1:48080/admin-api/infra/file/4/get/a4b34998498c5fc7c7cf17405fea27c4f4d70b30336d2d20ea59ce5ff7fa88c8.png\" style=\"width: 40.88px;height: 40.88px;\"><span style=\"font-family: 微软雅黑;\"> </span><span style=\"font-family: 微软雅黑;\"><strong>反馈港</strong></span></p><p style=\"text-align: center;\"><span style=\"font-family: 微软雅黑;\">邀请加入您</span><span style=\"font-family: 微软雅黑;\"><strong>{tenantName}</strong></span><span style=\"font-family: 微软雅黑;\">团队，点击下方链接：</span></p><p style=\"text-align: center;\"><a href=\"{inviteLink}\" target=\"_blank\">{inviteLink}</a></p>', '[\"tenantName\",\"inviteLink\",\"inviteLink\"]', 0, '', '1', '2023-11-30 17:03:33', '1', '2023-12-01 15:24:09', b'0');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `type` tinyint NOT NULL COMMENT '菜单类型',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父菜单ID',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '路由地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件名',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '菜单状态',
  `visible` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可见',
  `keep_alive` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否缓存',
  `always_show` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否总是显示',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100824 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES (2, '基础设施', '', 1, 99, 0, '/infra', 'fa-solid:campground', NULL, NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-12-04 16:28:24', b'0');
INSERT INTO `system_menu` VALUES (100, '管理团队', '', 2, 4, 0, '/user-team', 'fa-solid:users', 'system/userTeam/index', 'SystemUserTeam', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-12-04 16:25:27', b'0');
INSERT INTO `system_menu` VALUES (101, '角色管理', '', 2, 3, 0, '/role', 'fa-solid:user-circle', 'system/role/index', 'SystemRole', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-12-04 16:25:20', b'0');
INSERT INTO `system_menu` VALUES (102, '菜单管理', '', 2, 9, 0, '/menu', 'ep:menu', 'system/menu/index', 'SystemMenu', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-12-04 16:27:50', b'0');
INSERT INTO `system_menu` VALUES (105, '字典管理', '', 2, 11, 0, '/dictManage', 'ep:notebook', 'system/dict/index', 'SystemDictType', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-12-04 16:28:02', b'0');
INSERT INTO `system_menu` VALUES (106, '配置管理', '', 2, 6, 2, 'config', '', 'infra/config/index', 'InfraConfig', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:58:54', b'0');
INSERT INTO `system_menu` VALUES (108, '审计日志', '', 1, 12, 2321, 'auditlog', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:56:05', b'0');
INSERT INTO `system_menu` VALUES (110, '定时任务', '', 2, 8, 0, '/timeJob', 'ep:alarm-clock', 'infra/job/index', 'InfraJob', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-12-04 16:27:46', b'0');
INSERT INTO `system_menu` VALUES (111, 'MySQL 监控', '', 2, 9, 2322, 'druid', '', 'infra/druid/index', 'InfraDruid', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:56:07', b'0');
INSERT INTO `system_menu` VALUES (112, 'Java 监控', '', 2, 11, 2322, 'admin-server', '', 'infra/server/index', 'InfraAdminServer', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:56:08', b'0');
INSERT INTO `system_menu` VALUES (113, 'Redis 监控', '', 2, 10, 2322, 'redis', '', 'infra/redis/index', 'InfraRedis', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:56:11', b'0');
INSERT INTO `system_menu` VALUES (115, '代码生成', 'infra:codegen:query', 2, 1, 2, 'codegen', '', 'infra/codegen/index', 'InfraCodegen', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:58:25', b'0');
INSERT INTO `system_menu` VALUES (116, '接口文档', 'infra:swagger:list', 2, 3, 2320, 'swagger', '', 'infra/swagger/index', 'InfraSwagger', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:54:52', b'0');
INSERT INTO `system_menu` VALUES (500, '操作日志', '', 2, 1, 108, 'operate-log', '', 'system/operatelog/index', 'SystemOperateLog', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:57:24', b'0');
INSERT INTO `system_menu` VALUES (501, '登录日志', '', 2, 2, 108, 'login-log', '', 'system/loginlog/index', 'SystemLoginLog', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:57:29', b'0');
INSERT INTO `system_menu` VALUES (1001, '用户查询', 'system:user:query', 3, 1, 2309, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2023-11-22 22:50:17', b'0');
INSERT INTO `system_menu` VALUES (1002, '用户新增', 'system:user:create', 3, 2, 2309, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:50:17', b'0');
INSERT INTO `system_menu` VALUES (1003, '用户修改', 'system:user:update', 3, 3, 2309, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:50:17', b'0');
INSERT INTO `system_menu` VALUES (1004, '用户删除', 'system:user:delete', 3, 4, 2309, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:50:17', b'0');
INSERT INTO `system_menu` VALUES (1005, '用户导出', 'system:user:export', 3, 5, 2309, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2023-11-22 22:50:17', b'0');
INSERT INTO `system_menu` VALUES (1006, '用户导入', 'system:user:import', 3, 6, 2309, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2023-11-22 22:50:17', b'0');
INSERT INTO `system_menu` VALUES (1007, '重置密码', 'system:user:update-password', 3, 7, 2309, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-22 22:50:17', b'0');
INSERT INTO `system_menu` VALUES (1008, '角色查询', 'system:role:query', 3, 1, 101, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1009, '角色新增', 'system:role:create', 3, 2, 101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1010, '角色修改', 'system:role:update', 3, 3, 101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1011, '角色删除', 'system:role:delete', 3, 4, 101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1012, '角色导出', 'system:role:export', 3, 5, 101, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1013, '菜单查询', 'system:menu:query', 3, 1, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1014, '菜单新增', 'system:menu:create', 3, 2, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1015, '菜单修改', 'system:menu:update', 3, 3, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1016, '菜单删除', 'system:menu:delete', 3, 4, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1026, '字典查询', 'system:dict:query', 3, 1, 105, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1027, '字典新增', 'system:dict:create', 3, 2, 105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1028, '字典修改', 'system:dict:update', 3, 3, 105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1029, '字典删除', 'system:dict:delete', 3, 4, 105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1030, '字典导出', 'system:dict:export', 3, 5, 105, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1031, '配置查询', 'infra:config:query', 3, 1, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1032, '配置新增', 'infra:config:create', 3, 2, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1033, '配置修改', 'infra:config:update', 3, 3, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1034, '配置删除', 'infra:config:delete', 3, 4, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1035, '配置导出', 'infra:config:export', 3, 5, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1040, '操作查询', 'system:operate-log:query', 3, 1, 500, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1042, '日志导出', 'system:operate-log:export', 3, 2, 500, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1043, '登录查询', 'system:login-log:query', 3, 1, 501, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1045, '日志导出', 'system:login-log:export', 3, 3, 501, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1050, '任务新增', 'infra:job:create', 3, 2, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1051, '任务修改', 'infra:job:update', 3, 3, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1052, '任务删除', 'infra:job:delete', 3, 4, 110, '', '', '', '', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-11-10 15:55:21', b'0');
INSERT INTO `system_menu` VALUES (1054, '任务导出', 'infra:job:export', 3, 7, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1056, '生成修改', 'infra:codegen:update', 3, 2, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1057, '生成删除', 'infra:codegen:delete', 3, 3, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1058, '导入代码', 'infra:codegen:create', 3, 2, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1059, '预览代码', 'infra:codegen:preview', 3, 4, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1060, '生成代码', 'infra:codegen:download', 3, 5, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1063, '设置角色菜单权限', 'system:permission:assign-role-menu', 3, 6, 101, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-06 17:53:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1066, '获得 Redis 监控信息', 'infra:redis:get-monitor-info', 3, 1, 113, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-26 01:02:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1067, '获得 Redis Key 列表', 'infra:redis:get-key-list', 3, 2, 113, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-26 01:02:52', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1075, '任务触发', 'infra:job:trigger', 3, 8, 110, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-07 13:03:10', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1076, '数据库文档', '', 2, 4, 2320, 'db-doc', '', 'infra/dbDoc/index', 'InfraDBDoc', 0, b'1', b'1', b'1', '', '2021-02-08 01:41:47', '1', '2023-11-22 22:55:02', b'0');
INSERT INTO `system_menu` VALUES (1078, '访问日志', '', 2, 1, 1083, 'api-access-log', '', 'infra/apiAccessLog/index', 'InfraApiAccessLog', 0, b'1', b'1', b'1', '', '2021-02-26 01:32:59', '1', '2023-11-22 22:57:02', b'0');
INSERT INTO `system_menu` VALUES (1082, '日志导出', 'infra:api-access-log:export', 3, 2, 1078, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-26 01:32:59', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1083, 'API 日志', '', 2, 8, 2321, 'log', '', NULL, NULL, 0, b'1', b'1', b'1', '', '2021-02-26 02:18:24', '1', '2023-11-22 22:56:06', b'0');
INSERT INTO `system_menu` VALUES (1084, '错误日志', 'infra:api-error-log:query', 2, 2, 1083, 'api-error-log', '', 'infra/apiErrorLog/index', 'InfraApiErrorLog', 0, b'1', b'1', b'1', '', '2021-02-26 07:53:20', '1', '2023-11-22 22:57:04', b'0');
INSERT INTO `system_menu` VALUES (1085, '日志处理', 'infra:api-error-log:update-status', 3, 2, 1084, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1086, '日志导出', 'infra:api-error-log:export', 3, 3, 1084, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1087, '任务查询', 'infra:job:query', 3, 1, 110, '', '', '', '', 0, b'1', b'1', b'1', '1', '2021-03-10 01:26:19', '1', '2023-11-09 20:56:36', b'0');
INSERT INTO `system_menu` VALUES (1088, '日志查询', 'infra:api-access-log:query', 3, 1, 1078, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-03-10 01:28:04', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1089, '日志查询', 'infra:api-error-log:query', 3, 1, 1084, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-03-10 01:29:09', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1090, '文件列表', '', 2, 5, 1243, 'file', '', 'infra/file/index', 'InfraFile', 0, b'1', b'1', b'1', '', '2021-03-12 20:16:20', '1', '2023-11-22 22:59:32', b'0');
INSERT INTO `system_menu` VALUES (1091, '文件查询', 'infra:file:query', 3, 1, 1090, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-03-12 20:16:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1092, '文件删除', 'infra:file:delete', 3, 4, 1090, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-03-12 20:16:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1138, '租户列表', '', 2, 0, 1224, 'list', '', 'system/tenant/index', 'Tenant', 0, b'1', b'1', b'1', '', '2021-12-14 12:31:43', '1', '2023-11-22 16:45:13', b'0');
INSERT INTO `system_menu` VALUES (1139, '租户查询', 'system:tenant:query', 3, 1, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1141, '租户更新', 'system:tenant:update', 3, 3, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1142, '租户删除', 'system:tenant:delete', 3, 4, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1143, '租户导出', 'system:tenant:export', 3, 5, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1224, '租户管理', '', 2, 6, 0, '/tenant', 'fa-solid:house-user', NULL, NULL, 0, b'1', b'1', b'1', '1', '2022-02-20 01:41:13', '1', '2023-12-04 16:27:34', b'0');
INSERT INTO `system_menu` VALUES (1225, '租户套餐', '', 2, 0, 1224, 'package', '', 'system/tenantPackage/index', 'SystemTenantPackage', 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '1', '2023-11-21 19:09:25', b'0');
INSERT INTO `system_menu` VALUES (1226, '租户套餐查询', 'system:tenant-package:query', 3, 1, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1227, '租户套餐创建', 'system:tenant-package:create', 3, 2, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1228, '租户套餐更新', 'system:tenant-package:update', 3, 3, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1229, '租户套餐删除', 'system:tenant-package:delete', 3, 4, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1237, '文件配置', '', 2, 0, 1243, 'file-config', '', 'infra/fileConfig/index', 'InfraFileConfig', 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '1', '2023-11-22 22:59:30', b'0');
INSERT INTO `system_menu` VALUES (1238, '文件配置查询', 'infra:file-config:query', 3, 1, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1239, '文件配置创建', 'infra:file-config:create', 3, 2, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1240, '文件配置更新', 'infra:file-config:update', 3, 3, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1241, '文件配置删除', 'infra:file-config:delete', 3, 4, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1242, '文件配置导出', 'infra:file-config:export', 3, 5, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1243, '文件管理', '', 2, 5, 2, 'file', '', NULL, '', 0, b'1', b'1', b'1', '1', '2022-03-16 23:47:40', '1', '2023-11-22 22:58:46', b'0');
INSERT INTO `system_menu` VALUES (1247, '敏感词', '', 2, 7, 0, '/sensitive-word', 'ep:warning', 'system/sensitiveWord/index', 'SystemSensitiveWord', 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '1', '2023-12-04 16:27:39', b'0');
INSERT INTO `system_menu` VALUES (1248, '敏感词查询', 'system:sensitive-word:query', 3, 1, 1247, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1249, '敏感词创建', 'system:sensitive-word:create', 3, 2, 1247, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1250, '敏感词更新', 'system:sensitive-word:update', 3, 3, 1247, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1251, '敏感词删除', 'system:sensitive-word:delete', 3, 4, 1247, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1252, '敏感词导出', 'system:sensitive-word:export', 3, 5, 1247, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1255, '数据源配置', '', 2, 1, 2, 'data-source-config', '', 'infra/dataSourceConfig/index', 'InfraDataSourceConfig', 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '1', '2023-11-22 22:58:38', b'0');
INSERT INTO `system_menu` VALUES (1256, '数据源配置查询', 'infra:data-source-config:query', 3, 1, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1257, '数据源配置创建', 'infra:data-source-config:create', 3, 2, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1258, '数据源配置更新', 'infra:data-source-config:update', 3, 3, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1259, '数据源配置删除', 'infra:data-source-config:delete', 3, 4, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1260, '数据源配置导出', 'infra:data-source-config:export', 3, 5, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (2130, '邮箱管理', '', 2, 14, 0, '/mail', 'fa-solid:mail-bulk', NULL, NULL, 0, b'1', b'1', b'1', '1', '2023-01-25 17:27:44', '1', '2023-12-04 16:28:16', b'0');
INSERT INTO `system_menu` VALUES (2131, '邮箱账号', '', 2, 0, 2130, 'mail-account', '', 'system/mail/account/index', 'SystemMailAccount', 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '1', '2023-11-22 22:57:42', b'0');
INSERT INTO `system_menu` VALUES (2132, '账号查询', 'system:mail-account:query', 3, 1, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2133, '账号创建', 'system:mail-account:create', 3, 2, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2134, '账号更新', 'system:mail-account:update', 3, 3, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2135, '账号删除', 'system:mail-account:delete', 3, 4, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2136, '邮件模版', '', 2, 0, 2130, 'mail-template', '', 'system/mail/template/index', 'SystemMailTemplate', 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '1', '2023-11-22 22:57:43', b'0');
INSERT INTO `system_menu` VALUES (2137, '模版查询', 'system:mail-template:query', 3, 1, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2138, '模版创建', 'system:mail-template:create', 3, 2, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2139, '模版更新', 'system:mail-template:update', 3, 3, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2140, '模版删除', 'system:mail-template:delete', 3, 4, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2141, '邮件记录', '', 2, 0, 2130, 'mail-log', '', 'system/mail/log/index', 'SystemMailLog', 0, b'1', b'1', b'1', '', '2023-01-26 02:16:50', '1', '2023-11-22 22:57:49', b'0');
INSERT INTO `system_menu` VALUES (2142, '日志查询', 'system:mail-log:query', 3, 1, 2141, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-26 02:16:50', '', '2023-01-26 02:16:50', b'0');
INSERT INTO `system_menu` VALUES (2143, '发送测试邮件', 'system:mail-template:send-mail', 3, 5, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-26 23:29:15', '1', '2023-01-26 23:29:15', b'0');
INSERT INTO `system_menu` VALUES (2309, '用户管理', '', 2, 5, 0, '/userManage', 'ep:user', 'system/user/index', 'SystemUser', 0, b'1', b'1', b'1', '1', '2023-08-14 21:00:49', '1', '2023-12-04 16:25:33', b'0');
INSERT INTO `system_menu` VALUES (2310, '用户反馈', '', 1, 1, 0, '/feedback', 'ep:postcard', '', '', 0, b'1', b'1', b'1', '1', '2023-08-19 15:00:27', '1', '2023-12-04 16:25:09', b'0');
INSERT INTO `system_menu` VALUES (2311, '反馈标签', '', 2, 2, 0, '/feedbackTag', 'fa-solid:tags', 'harbor/feedbackTag/index', 'HarborFeedbackTag', 0, b'1', b'1', b'1', '1', '2023-10-04 18:11:55', '1', '2023-12-04 16:25:13', b'0');
INSERT INTO `system_menu` VALUES (2312, '新增', 'harbor:feedback-tag:create', 3, 1, 2311, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-10-05 21:00:21', '1', '2023-10-06 15:33:19', b'0');
INSERT INTO `system_menu` VALUES (2313, '编辑', 'harbor:feedback-tag:update', 3, 2, 2311, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-10-06 14:36:23', '1', '2023-10-06 15:34:12', b'0');
INSERT INTO `system_menu` VALUES (2314, '删除', 'harbor:feedback-tag:delete', 3, 3, 2311, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-10-06 15:33:52', '1', '2023-10-06 15:33:52', b'0');
INSERT INTO `system_menu` VALUES (2316, '所有', '', 2, 1, 2310, 'FeedbackAll', '', 'harbor/Feedback/All/index', '', 0, b'1', b'1', b'1', '1', '2023-10-29 01:09:43', '1', '2023-10-29 01:50:52', b'0');
INSERT INTO `system_menu` VALUES (2317, '未回复', '', 2, 2, 2310, 'UnReply', '', 'harbor/Feedback/UnReply/index', '', 0, b'1', b'1', b'1', '1', '2023-10-29 01:50:45', '1', '2023-10-29 01:51:03', b'0');
INSERT INTO `system_menu` VALUES (2319, '查询', 'harbor:feedback-tag:query', 3, 4, 2311, '', '', '', '', 0, b'1', b'1', b'1', '129', '2023-10-31 16:41:25', '129', '2023-10-31 16:41:25', b'0');
INSERT INTO `system_menu` VALUES (2320, '系统文档', '', 1, 10, 0, '/docment', 'ep:folder-opened', '', '', 0, b'1', b'1', b'1', '1', '2023-11-11 02:30:04', '1', '2023-12-04 16:27:55', b'0');
INSERT INTO `system_menu` VALUES (2321, '系统日志', '', 1, 12, 0, '/systemlog', 'fa-solid:record-vinyl', '', '', 0, b'1', b'1', b'1', '1', '2023-11-11 02:34:42', '1', '2023-12-04 16:28:06', b'0');
INSERT INTO `system_menu` VALUES (2322, '系统监控', '', 1, 13, 0, '/monitoring', 'ep:monitor', '', '', 0, b'1', b'1', b'1', '1', '2023-11-11 02:38:08', '1', '2023-12-04 16:28:11', b'0');
INSERT INTO `system_menu` VALUES (100594, '赋予角色', 'system:permission:assign-user-role', 3, 1, 100, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-23 15:19:46', '1', '2023-11-23 15:19:46', b'0');
INSERT INTO `system_menu` VALUES (100633, '列表', 'system:userteam:list', 3, 2, 100, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-23 15:21:25', '1', '2023-11-23 15:21:25', b'0');
INSERT INTO `system_menu` VALUES (100765, '邀请', 'system:invite:invite', 3, 3, 100, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-25 04:44:27', '1', '2023-11-28 20:57:35', b'0');
INSERT INTO `system_menu` VALUES (100823, '退出', 'system:userteam:quit', 3, 4, 100, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-26 10:27:13', '1', '2023-11-26 10:27:13', b'0');
INSERT INTO `system_menu` VALUES (101518, '删除', 'harbor:feedback:delete', 3, 1, 2316, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-03 18:24:34', '1', '2023-12-03 18:24:34', b'0');
INSERT INTO `system_menu` VALUES (101535, '列表', 'harbor:feedback:query', 3, 2, 2316, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-03 18:25:46', '1', '2023-12-03 18:25:46', b'0');

-- ----------------------------
-- Table structure for system_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `system_operate_log`;
CREATE TABLE `system_operate_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块标题',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作名',
  `type` bigint NOT NULL DEFAULT 0 COMMENT '操作分类',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作内容',
  `exts` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求地址',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户 IP',
  `user_agent` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '浏览器 UA',
  `java_method` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'Java 方法名',
  `java_method_args` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'Java 方法的参数',
  `start_time` datetime NOT NULL COMMENT '操作时间',
  `duration` int NOT NULL COMMENT '执行时长',
  `result_code` int NOT NULL DEFAULT 0 COMMENT '结果码',
  `result_msg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果提示',
  `result_data` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果数据',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725808617529839617 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_operate_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `sort` int NOT NULL COMMENT '显示顺序',
  `status` tinyint NOT NULL COMMENT '角色状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100926 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES (1, '超级管理员', 'super_admin', 1, 0, '超级管理员', '', '2021-01-05 17:03:48', '', '2023-11-10 21:38:52', b'0', 1);

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100964 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for system_sensitive_word
-- ----------------------------
DROP TABLE IF EXISTS `system_sensitive_word`;
CREATE TABLE `system_sensitive_word`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '敏感词',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签数组',
  `status` tinyint NOT NULL COMMENT '状态',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725799250256498691 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '敏感词' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_sensitive_word
-- ----------------------------
INSERT INTO `system_sensitive_word` VALUES (101671, '中国', '', '政治', 0, '1', '2023-12-03 18:52:41', '1', '2023-12-03 18:52:41', b'0');

-- ----------------------------
-- Table structure for system_tenant
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant`;
CREATE TABLE `system_tenant`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '租户编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户名',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）',
  `package_id` bigint NULL DEFAULT NULL COMMENT '租户套餐编号',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户logo',
  `router_uri` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户路由uri',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725073042153771010 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_tenant
-- ----------------------------
INSERT INTO `system_tenant` VALUES (1, '反馈港', 0, 0, '2099-02-19 17:14:16', '', '2021-01-05 17:03:47', '', '2023-11-16 22:47:22', b'0', 'http://127.0.0.1:48080/admin-api/infra/file/4/get/Logo.png', 'feedback-harbor');

-- ----------------------------
-- Table structure for system_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant_package`;
CREATE TABLE `system_tenant_package`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '套餐编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '套餐名',
  `days` int NOT NULL COMMENT '套餐天数',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  `menu_ids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联的菜单编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100438 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户套餐表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_tenant_package
-- ----------------------------
INSERT INTO `system_tenant_package` VALUES (1, '普通套餐', -1, '租户默认套餐', '[1248,1249,1250,1251,1252,2309,2310,2311,2312,2313,2314,2316,2317,2318,2319,1247]', '1', '2022-02-22 00:54:00', '1', '2023-11-22 18:22:28', b'0');
INSERT INTO `system_tenant_package` VALUES (100438, '会员套餐', 30, '', '[100,101,2310,2311,1063,2312,2313,2314,2316,2317,2318,101518,2319,1008,1009,1010,1011,100594,1012,100823,100633,100765,101535]', '1', '2023-11-22 18:14:28', '1', '2023-12-03 18:25:55', b'0');

-- ----------------------------
-- Table structure for system_tenant_user
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant_user`;
CREATE TABLE `system_tenant_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '租户和用户关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_tenant_user
-- ----------------------------
INSERT INTO `system_tenant_user` VALUES (1, 1, 1, '2023-11-03 13:47:36', '2023-11-03 13:47:36', NULL, NULL, 0);

-- ----------------------------
-- Table structure for system_token_access
-- ----------------------------
DROP TABLE IF EXISTS `system_token_access`;
CREATE TABLE `system_token_access`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '访问令牌',
  `refresh_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '刷新令牌',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_ids` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '已授权的社区租户id集合',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725807246265061378 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'Token 访问令牌' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_token_access
-- ----------------------------

-- ----------------------------
-- Table structure for system_token_refresh
-- ----------------------------
DROP TABLE IF EXISTS `system_token_refresh`;
CREATE TABLE `system_token_refresh`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `refresh_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '刷新令牌',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725807246239895554 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'Token 刷新令牌' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_token_refresh
-- ----------------------------

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100948 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES (1, 1, 1, '', '2022-01-11 13:19:45', '', '2023-11-10 21:34:39', b'0', 1);

-- ----------------------------
-- Table structure for system_users
-- ----------------------------
DROP TABLE IF EXISTS `system_users`;
CREATE TABLE `system_users`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号码',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `social_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交类型',
  `social_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交openid',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC, `update_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1725557485981245443 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_users
-- ----------------------------
INSERT INTO `system_users` VALUES (1, 'admin', '$2a$10$mRMIYLDtRHlf6.9ipiqH1.Z.bh/R9dO9d5iHiGYPigi6r5KOoR2Wm', 'hehong', '管理员', '', '', 'https://foruda.gitee.com/avatar/1677132744735503292/8411184_theheboy_1608519664.png', 0, '0:0:0:0:0:0:0:1', '2023-12-03 18:26:16', '', '2021-01-05 17:03:47', NULL, '2023-12-03 18:26:16', b'0', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
