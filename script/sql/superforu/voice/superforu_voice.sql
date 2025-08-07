-- ### 主模块: 语音功能 DB设计 ####
-- ### 前缀：voice_
-- ### 版本：V1.0
-- ### 作者：Moks.Mo
-- #######################

-- 语音识别(字典配置：voice_)

-- 电话机设备
DROP TABLE IF EXISTS `voice_devices`;
CREATE TABLE `voice_devices`  (
                                  id                           bigint       NOT NULL                               COMMENT 'ID',
                                  device_id                    varchar(36)  NULL DEFAULT ''                        COMMENT '设备ID',
                                  device_name                  varchar(128) NULL DEFAULT ''                        COMMENT '设备名称',
                                  device_type                  varchar(12)  NULL DEFAULT ''                        COMMENT '设备类型',
                                  face_switch                  varchar(12)  NULL DEFAULT ''                        COMMENT '人脸识别功能',
                                  voice_switch                 varchar(12)  NULL DEFAULT ''                        COMMENT '语音识别功能',

                                  tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                  pic_url                      varchar(128) NULL DEFAULT ''                        COMMENT '图片地址',
                                  create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                  create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                  create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                  update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                  update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                  del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                  PRIMARY KEY (`id`) USING BTREE,
                                  KEY `idx_device_name` (`device_name`) USING BTREE

) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment = '电话机设备';

-- 敏感词设置
DROP TABLE IF EXISTS `voice_sensitive_words_setting`;
CREATE TABLE `voice_sensitive_words_setting`  (
                                 id                           bigint       NOT NULL                               COMMENT 'ID',
                                 sensitive_words              varchar(36)  NULL DEFAULT ''                        COMMENT '敏感词',

                                 tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                 pic_url                      varchar(128) NULL DEFAULT ''                        COMMENT '图片地址',
                                 create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                 create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                 create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                 update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                 update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                 del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `idx_sensitive_words` (`sensitive_words`) USING BTREE

) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment = '敏感词设置';

-- 语音分析记录
DROP TABLE IF EXISTS `voice_analysis_record`;
CREATE TABLE `voice_analysis_record`  (
                                                  id                           bigint       NOT NULL                               COMMENT 'ID',
                                                  device_id                    varchar(36)  NULL DEFAULT ''                        COMMENT '设备ID',
                                                  analysis_time                datetime     DEFAULT NULL                           COMMENT '分析时间',
                                                  sensitive_words              varchar(36)  NULL DEFAULT ''                        COMMENT '违规敏感词',
                                                  call_time                    datetime     DEFAULT NULL                           COMMENT '拨号开始时间',
                                                  call_time_length             int          NULL DEFAULT 0                         COMMENT '通话时厂(s)',
                                                  user_name                    varchar(36)  NULL DEFAULT ''                        COMMENT '使用人姓名',
                                                  local_phone_number           varchar(36)  NULL DEFAULT ''                        COMMENT '本机号码',
                                                  phone_status                 varchar(12)  NULL DEFAULT ''                        COMMENT '电话状态',
                                                  phone_number                 varchar(36)  NULL DEFAULT ''                        COMMENT '电话号码',
                                                  phone_name                   varchar(36)  NULL DEFAULT ''                        COMMENT '人员姓名',


                                                  tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                                  pic_url                      varchar(128) NULL DEFAULT ''                        COMMENT '图片地址',
                                                  create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                                  create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                                  create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                                  update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                                  update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                                  del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                                  PRIMARY KEY (`id`) USING BTREE,
                                                  KEY `idx_device_id` (`device_id`) USING BTREE

) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment = '语音分析记录';

