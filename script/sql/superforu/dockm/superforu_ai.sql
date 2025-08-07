-- ### 子模块：知识AI DB设计 ####
-- ### 前缀：ai_
-- ### 版本：V1.0
-- ### 作者：Moks、Spark
-- #######################

-- AI模型配置
DROP TABLE IF EXISTS `ai_model`;
CREATE TABLE `ai_model`  (
                                 id                           bigint       NOT NULL                               COMMENT 'ID',
                                 code                         varchar(64)  NOT NULL                               COMMENT '模型编码',
                                 name                         varchar(64)  NOT NULL                               COMMENT '模型名称',
                                 config_json                  varchar(1024) NULL                                   COMMENT '配置信息JSON',

                                 tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                 pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                 create_time                  datetime(0)  NULL DEFAULT NULL                      COMMENT '创建时间',
                                 create_by                    bigint(0)    NULL DEFAULT NULL                      COMMENT '创建人',
                                 update_time                  datetime(0)  NULL DEFAULT NULL                      COMMENT '更新时间',
                                 update_by                    bigint(0)    NULL DEFAULT NULL                      COMMENT '更新人',
                                 del_flag                     int(0)       NULL DEFAULT 0                         COMMENT '删除标志',

                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `idx_name` (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 对话历史
DROP TABLE IF EXISTS `ai_history`;
CREATE TABLE `ai_history`  (
                                id                           bigint       NOT NULL                               COMMENT 'ID',
                                tile                         varchar(64)  NOT NULL                               COMMENT '标题',
                                link_model_code              varchar(64)  NOT NULL                               COMMENT '模型编码',
                                link_model_name              varchar(64)  NULL                                   COMMENT '模型名称',
                                link_aiai_id                 bigint          NOT NULL                            COMMENT '永久知识库ID',
                                link_user_id                 bigint       NULL                                   COMMENT '用户ID',

                                tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                create_time                  datetime(0)  NULL DEFAULT NULL                      COMMENT '创建时间',
                                create_by                    bigint(0)    NULL DEFAULT NULL                      COMMENT '创建人',
                                update_time                  datetime(0)  NULL DEFAULT NULL                      COMMENT '更新时间',
                                update_by                    bigint(0)    NULL DEFAULT NULL                      COMMENT '更新人',
                                del_flag                     int(0)       NULL DEFAULT 0                         COMMENT '删除标志',

                                PRIMARY KEY (`id`) USING BTREE,
                                KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 临时文档
DROP TABLE IF EXISTS `ai_history`;
CREATE TABLE `ai_history`  (
                                 id                           bigint       NOT NULL                               COMMENT 'ID',
                                 name                         varchar(64)  NOT NULL                               COMMENT '名称',
                                 attr_id                      varchar(64)  NULL                                   COMMENT '临时文档ID',
                                 link_user_id                 bigint       NULL                                   COMMENT '用户ID',

                                 tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                 pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                 create_time                  datetime(0)  NULL DEFAULT NULL                      COMMENT '创建时间',
                                 create_by                    bigint(0)    NULL DEFAULT NULL                      COMMENT '创建人',
                                 update_time                  datetime(0)  NULL DEFAULT NULL                      COMMENT '更新时间',
                                 update_by                    bigint(0)    NULL DEFAULT NULL                      COMMENT '更新人',
                                 del_flag                     int(0)       NULL DEFAULT 0                         COMMENT '删除标志',

                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 我的创建的AI & AI知识星球
DROP TABLE IF EXISTS `ai_history`;
CREATE TABLE `ai_history`  (
                                 id                           bigint       NOT NULL                               COMMENT 'ID',
                                 name                         varchar(64)  NOT NULL                               COMMENT '名称',
                                 link_ai_model_id             bigint       NULL                                   COMMENT 'AI模型ID',
                                 link_ai_ai_id                bigint       NULL                                   COMMENT 'AI知识库ID',
                                 access_permissions           varchar(1024) NOT NULL                               COMMENT '访问权限：all:全部人访问、teamId：指定团队',
                                 link_user_id                 bigint       NULL                                   COMMENT '用户ID',
                                 link_user_name               varchar(64)  NULL                                   COMMENT '用户名称',
                                 mark                         varchar(500) NULL                                   COMMENT '描述',

                                 count                        int          NULL DEFAULT 0                         COMMENT '使用总人数',

                                 tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                 pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                 create_time                  datetime(0)  NULL DEFAULT NULL                      COMMENT '创建时间',
                                 create_by                    bigint(0)    NULL DEFAULT NULL                      COMMENT '创建人',
                                 update_time                  datetime(0)  NULL DEFAULT NULL                      COMMENT '更新时间',
                                 update_by                    bigint(0)    NULL DEFAULT NULL                      COMMENT '更新人',
                                 del_flag                     int(0)       NULL DEFAULT 0                         COMMENT '删除标志',

                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;





