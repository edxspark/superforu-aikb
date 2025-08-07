-- ### 主模块: AI标书 DB设计 ####
-- ### 前缀：bid_
-- ### 版本：V1.0
-- ### 作者：Moks.Mo
-- #######################

-- AI标书(字典配置：bid_)

-- 公司资料
DROP TABLE IF EXISTS `bid_company_doc`;
CREATE TABLE `bid_company_doc`  (
                                  id                           bigint       NOT NULL                               COMMENT 'ID',
                                  link_user_id                 bigint       NULL                                   COMMENT '用户ID',
                                  company_doc_data             text         NULL DEFAULT NULL                      COMMENT '公司资料',

                                  tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                  pic_url                      varchar(128) NULL DEFAULT ''                        COMMENT '图片地址',
                                  create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                  create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                  create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                  update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                  update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                  del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                  PRIMARY KEY (`id`) USING BTREE,
                                  KEY `idx_link_user_id` (`link_user_id`) USING BTREE

) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment = '公司资料';

-- 公司资料
DROP TABLE IF EXISTS `bid_project_doc`;
CREATE TABLE `bid_project_doc`  (
                                    id                           bigint       NOT NULL                               COMMENT 'ID',
                                    project_name                 varchar(128) DEFAULT ''                             COMMENT '项目名称',
                                    link_user_id                 bigint       NULL                                   COMMENT '用户ID',
                                    project_doc_data             text         NULL DEFAULT NULL                      COMMENT '项目资料',

                                    tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                    pic_url                      varchar(128) NULL DEFAULT ''                        COMMENT '图片地址',
                                    create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                    create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                    create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                    update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                    update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                    del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                    PRIMARY KEY (`id`) USING BTREE,
                                    KEY `idx_link_user_id` (`link_user_id`) USING BTREE

) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment = '项目资料';


-- 写标任务
DROP TABLE IF EXISTS `bid_generation_task`;
CREATE TABLE `bid_generation_task`  (
                                        id                           bigint       NOT NULL                               COMMENT 'ID',
                                        link_user_id                 bigint       NULL                                   COMMENT '用户ID',
                                        project_id                   bigint       NULL                                   COMMENT '项目ID',
                                        project_name                 varchar(256) NULL                                   COMMENT '项目名称',
                                        bid_type_id                  bigint       NULL                                   COMMENT '标书类型ID',
                                        bid_type_name                varchar(12)  NULL                                   COMMENT '标书类型',
                                        bid_catalog_id               bigint       NULL                                   COMMENT '标书目录ID',
                                        bid_catalog_doc              text         NULL                                   COMMENT '标书目录内容',
                                        bid_core_doc                 text         NULL                                   COMMENT '标书核心要求',
                                        bid_url                      varchar(256) NULL                                   COMMENT '标书URL',
                                        model                        varchar(32)  NULL                                   COMMENT '生成模型',
                                        other_files                  varchar(2048) NULL                                  COMMENT '其他文件URL',
                                        doc_style                    varchar(10) NULL                                    COMMENT '板书样式',

                                        tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                        pic_url                      varchar(128) NULL DEFAULT ''                        COMMENT '图片地址',
                                        create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                        create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                        create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                        update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                        update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                        del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                        PRIMARY KEY (`id`) USING BTREE,
                                        KEY `idx_link_user_id` (`link_user_id`) USING BTREE

) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment = '写标任务';

