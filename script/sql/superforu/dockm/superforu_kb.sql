-- ### 子模块：知识库 DB设计 ####
-- ### 前缀：kb_
-- ### 版本：V1.0
-- ### 作者：Moks、Spark
-- #######################

-- 知识库
DROP TABLE IF EXISTS `kb_km`;
CREATE TABLE `kb_km`  (
                                id                           bigint       NOT NULL                               COMMENT 'ID',
                                name                         varchar(64)  NOT NULL                               COMMENT '名称',
                                type                         varchar(10)  NULL                                   COMMENT '类型',
                                status                       int(0)       DEFAULT 0                              COMMENT '状态',
                                is_ai_open                   int(0)       DEFAULT 0                              COMMENT '是否开启AI',
                                agent_type                   varchar(10)  DEFAULT 'COM'                          COMMENT '智能体类型',
                                mark                         varchar(64)  NULL                                   COMMENT '介绍',
                                link_user_account            varchar(64)  NOT NULL                               COMMENT '账号',
                                link_cycle_id                bigint        NULL                                  COMMENT '回收站ID',
                                max_space                    bigint(40)   NULL DEFAULT 0                         COMMENT '最大磁盘空间(B)',
                                used_space                   bigint(40)   NULL DEFAULT 0                         COMMENT '已使用磁盘空间(B)',
                                out_project_id               bigint       NULL                                   COMMENT '集成项目ID',
                                out_storage_id               bigint       NULL                                   COMMENT '集成存储ID',
                                file_km_tree_data            text         NULL DEFAULT NULL                      COMMENT '知识文档树数据',

                                tenant_id                    varchar(20)   DEFAULT '000000'                      COMMENT '租户ID',
                                pic_url                      varchar(255)  DEFAULT ''                            COMMENT '图片地址',
                                create_time                  datetime(0)   DEFAULT NULL                          COMMENT '创建时间',
                                create_dept                  bigint(20)    DEFAULT NULL                          COMMENT '创建部门',
                                create_by                    bigint(0)     DEFAULT NULL                          COMMENT '创建人',
                                update_time                  datetime(0)   DEFAULT NULL                          COMMENT '更新时间',
                                update_by                    bigint(0)     DEFAULT NULL                          COMMENT '更新人',
                                del_flag                     int(0)        DEFAULT 0                             COMMENT '删除标志',

                                PRIMARY KEY (`id`) USING BTREE,
                                KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
                                KEY `idx_name` (`name`) USING BTREE,
                                KEY `idx_link_user_account` (`link_user_account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='知识库';

-- 知识库协同管理
DROP TABLE IF EXISTS `kb_km_collaboration`;
CREATE TABLE `kb_km_collaboration`  (
                          id                           bigint        NOT NULL                               COMMENT 'ID',
                          link_km_id                   bigint        NOT NULL                               COMMENT '知识库ID',
                          link_team_id                 varchar(64)   NOT NULL                               COMMENT '团队ID',
                          status                       int(0)        DEFAULT 0                              COMMENT '状态',
                          link_cycle_id                bigint        NULL                                   COMMENT '回收站ID',

                          tenant_id                    varchar(20)   DEFAULT '000000'                       COMMENT '租户ID',
                          pic_url                      varchar(255)  DEFAULT ''                             COMMENT '图片地址',
                          create_time                  datetime(0)   DEFAULT NULL                           COMMENT '创建时间',
                          create_dept                  bigint(20)    DEFAULT NULL                           COMMENT '创建部门',
                          create_by                    bigint(0)     DEFAULT NULL                           COMMENT '创建人',
                          update_time                  datetime(0)   DEFAULT NULL                           COMMENT '更新时间',
                          update_by                    bigint(0)     DEFAULT NULL                           COMMENT '更新人',
                          del_flag                     int(0)        DEFAULT 0                              COMMENT '删除标志',

                          PRIMARY KEY (`id`) USING BTREE,
                          KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
                          KEY `idx_link_km_id` (`link_km_id`) USING BTREE,
                          KEY `idx_link_team_id` (`link_team_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='知识库协同管理';

-- 知识库分享预览
DROP TABLE IF EXISTS `kb_km_share`;
CREATE TABLE `kb_km_share`  (
                          id                           bigint        NOT NULL                                COMMENT 'ID',
                          share_id                     varchar(64)   DEFAULT ''                              COMMENT '分享ID',
                          link_km_id                   bigint        NOT NULL                                COMMENT '知识库ID',
                          access_permission            varchar(64)   DEFAULT ''                              COMMENT '访问权限',
                          access_values                varchar(1024) DEFAULT ''                              COMMENT '访问权限值',
                          access_password              varchar(6)    DEFAULT ''                              COMMENT '访问权密码',
                          status                       int(0)        DEFAULT 0                               COMMENT '状态',
                          link_cycle_id                bigint        NULL                                    COMMENT '回收站ID',

                          tenant_id                    varchar(20)  DEFAULT '000000'                        COMMENT '租户ID',
                          pic_url                      varchar(255) DEFAULT ''                              COMMENT '图片地址',
                          create_time                  datetime(0)  DEFAULT NULL                            COMMENT '创建时间',
                          create_dept                  bigint(20)   DEFAULT NULL                            COMMENT '创建部门',
                          create_by                    bigint(0)    DEFAULT NULL                            COMMENT '创建人',
                          update_time                  datetime(0)  DEFAULT NULL                            COMMENT '更新时间',
                          update_by                    bigint(0)    DEFAULT NULL                            COMMENT '更新人',
                          del_flag                     int(0)       DEFAULT 0                               COMMENT '删除标志',

                          PRIMARY KEY (`id`) USING BTREE,
                          KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
                          KEY `idx_share_id` (`share_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='知识库分享预览';


-- 文件类型
DROP TABLE IF EXISTS `kb_file_type`;
CREATE TABLE `kb_file_type`  (
                           id                           bigint        NOT NULL                               COMMENT 'ID',
                           name                         varchar(64)   NOT NULL                               COMMENT '类型名称',
                           code                         varchar(64)   NOT NULL                               COMMENT '类型ID',
                           sort                         int(2)        NOT NULL                               COMMENT '序号',
                           icon                         varchar(24)   NOT NULL                               COMMENT '类型ICON',
                           color                        varchar(24)   NOT NULL                               COMMENT '类型颜色',
                           attr_type                    varchar(10)   NULL                                   COMMENT '后缀名',

                           tenant_id                    varchar(20)   DEFAULT '000000'                       COMMENT '租户ID',
                           pic_url                      varchar(255)  DEFAULT ''                             COMMENT '图片地址',
                           create_time                  datetime(0)   DEFAULT NULL                           COMMENT '创建时间',
                           create_dept                  bigint(20)    DEFAULT NULL                           COMMENT '创建部门',
                           create_by                    bigint(0)     DEFAULT NULL                           COMMENT '创建人',
                           update_time                  datetime(0)   DEFAULT NULL                           COMMENT '更新时间',
                           update_by                    bigint(0)     DEFAULT NULL                           COMMENT '更新人',
                           del_flag                     int(0)        DEFAULT 0                              COMMENT '删除标志',

                           PRIMARY KEY (`id`) USING BTREE,
                           KEY `idx_code` (`code`) USING BTREE,
                           KEY `idx_name` (`name`) USING BTREE,
                           KEY `idx_sort` (`sort`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='文件类型';

-- 文件夹&文件
DROP TABLE IF EXISTS `kb_folder_file`;
CREATE TABLE `kb_folder_file`  (
                             id                           bigint        NOT NULL                                COMMENT '主键',
                             file_name                    varchar(128)  NULL                                    COMMENT '文件名称',
                             file_extension               varchar(12)   NULL                                    COMMENT '文件后缀',
                             parent_id                    bigint        NULL DEFAULT 0                          COMMENT '父类ID',
                             catalog_ids                  varchar(1024) NULL DEFAULT NULL                       COMMENT '当前文件目录IDS',
                             is_folder                    int           NULL                                    COMMENT '是否文件夹',
                             sort                         int(10)       NULL DEFAULT 0                          COMMENT '排序',
                             status                       int(0)        DEFAULT 0                               COMMENT '状态',
                             ai_status                    int(0)        DEFAULT 0                               COMMENT 'AI状态',
                             ai_sync_time                 datetime(0)   DEFAULT '1990-01-01 00:00:00'           COMMENT 'AI同步时间',
                             link_user_id                 bigint        NULL                                    COMMENT '用户ID',
                             link_user_name               varchar(64)   NULL                                    COMMENT '用户名称',
                             link_km_id                   bigint        NOT NULL                                COMMENT '知识库ID',
                             editing                      bit           NULL                                    COMMENT '编辑状态',
                             link_file_type_code          varchar(64)   NULL                                    COMMENT '文件类型ID',
                             link_file_type_name          varchar(64)   NULL                                    COMMENT '文件类型名称',
                             link_file_content_id         bigint        NULL                                    COMMENT '文件内容ID',
                             file_space                   bigint(40)    NOT NULL DEFAULT 0                      COMMENT '文件占用空间(B)',
                             link_cycle_id                bigint        NULL                                    COMMENT '回收站ID',
                             file_km_tree_data            text          NULL DEFAULT NULL                       COMMENT '知识文档树数据',

                             tenant_id                    varchar(20)   DEFAULT '000000'                        COMMENT '租户ID',
                             pic_url                      varchar(255)  DEFAULT ''                              COMMENT '图片地址',
                             create_time                  datetime(0)   DEFAULT NULL                            COMMENT '创建时间',
                             create_dept                  bigint(20)    DEFAULT NULL                            COMMENT '创建部门',
                             create_by                    bigint(0)     DEFAULT NULL                            COMMENT '创建人',
                             update_time                  datetime(0)   DEFAULT NULL                            COMMENT '更新时间',
                             update_by                    bigint(0)     DEFAULT NULL                            COMMENT '更新人',
                             del_flag                     int(0)        DEFAULT 0                               COMMENT '删除标志',
                             PRIMARY KEY (`id`) USING BTREE,
                             KEY `idx_link_km_id` (`link_km_id`) USING BTREE,
                             KEY `idx_parent_id` (`parent_id`) USING BTREE,
                             KEY `idx_file_name` (`file_name`) USING BTREE,
                             KEY `idx_is_folder` (`is_folder`) USING BTREE,
                             KEY `idx_link_file_type_code` (`link_file_type_code`) USING BTREE,
                             KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='文件夹&文件';



-- 文件内容
DROP TABLE IF EXISTS `kb_file_content`;
CREATE TABLE `kb_file_content`  (
                             id                           bigint        NOT NULL                                COMMENT '主键',
                             status                       int(0)        DEFAULT 0                               COMMENT '状态',
                             link_file_id                 bigint        NOT NULL                                COMMENT '文件ID',
                             link_km_id                   bigint        NOT NULL                                COMMENT '知识库ID',
                             catalog_ids                  varchar(1024) NULL DEFAULT NULL                       COMMENT '当前文件目录IDS',
                             link_file_code               varchar(64)   NULL                                    COMMENT '文件类型ID',
                             link_file_content            longblob      NULL                                    COMMENT '文件内容',
                             link_cycle_id                bigint        NULL                                    COMMENT '回收站ID',

                             tenant_id                    varchar(20)   DEFAULT '000000'                        COMMENT '租户ID',
                             pic_url                      varchar(255)  DEFAULT ''                              COMMENT '图片地址',
                             create_time                  datetime(0)   DEFAULT NULL                            COMMENT '创建时间',
                             create_dept                  bigint(20)    DEFAULT NULL                            COMMENT '创建部门',
                             create_by                    bigint(0)     DEFAULT NULL                            COMMENT '创建人',
                             update_time                  datetime(0)   DEFAULT NULL                            COMMENT '更新时间',
                             update_by                    bigint(0)     DEFAULT NULL                            COMMENT '更新人',
                             del_flag                     int(0)        DEFAULT 0                               COMMENT '删除标志',
                             PRIMARY KEY (`id`) USING BTREE,
                             KEY `idx_file_id` (`link_file_id`) USING BTREE,
                             KEY `idx_link_km_id` (`link_km_id`) USING BTREE,
                             KEY `idx_catalog_ids` (`catalog_ids`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='文件内容';


-- 最近编辑
DROP TABLE IF EXISTS `kb_file_recently`;
CREATE TABLE `kb_file_recently`  (
                                    id                           bigint        NOT NULL                                COMMENT '主键',
                                    link_file_id                 bigint        NOT NULL                                COMMENT '文件ID',
                                    link_user_id                 bigint        NOT NULL                                COMMENT '用户ID',
                                    file_name                    varchar(64)   NULL                                    COMMENT '文件名称',
                                    file_type                    varchar(24)   NULL                                    COMMENT '文件类型',
                                    file_abstract                varchar(1024) NULL                                    COMMENT '文件摘要',

                                    tenant_id                    varchar(20)   DEFAULT '000000'                        COMMENT '租户ID',
                                    pic_url                      varchar(255)  DEFAULT ''                              COMMENT '图片地址',
                                    create_time                  datetime(0)   DEFAULT NULL                            COMMENT '创建时间',
                                    create_dept                  bigint(20)    DEFAULT NULL                            COMMENT '创建部门',
                                    create_by                    bigint(0)     DEFAULT NULL                            COMMENT '创建人',
                                    update_time                  datetime(0)   DEFAULT NULL                            COMMENT '更新时间',
                                    update_by                    bigint(0)     DEFAULT NULL                            COMMENT '更新人',
                                    del_flag                     int(0)        DEFAULT 0                               COMMENT '删除标志',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    KEY `link_file_id` (`link_file_id`) USING BTREE,
                                    KEY `idx_link_user_id` (`link_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='最近编辑';



-- 文件历史
DROP TABLE IF EXISTS `kb_file_history`;
CREATE TABLE `kb_file_history`  (
                                     id                           bigint        NOT NULL                                COMMENT '主键',
                                     link_user_id                 bigint        NULL                                    COMMENT '用户ID',
                                     link_user_name               varchar(64)   NULL                                    COMMENT '用户名称',
                                     link_file_id                 bigint        NOT NULL                                COMMENT '文件ID',
                                     file_content                 longblob      NULL                                    COMMENT '文件内容',
                                     mark                         varchar(500)  NULL                                    COMMENT '提交备注',

                                     tenant_id                    varchar(20)   DEFAULT '000000'                        COMMENT '租户ID',
                                     pic_url                      varchar(255)  DEFAULT ''                              COMMENT '图片地址',
                                     create_time                  datetime(0)   DEFAULT NULL                            COMMENT '创建时间',
                                     create_dept                  bigint(20)    DEFAULT NULL                            COMMENT '创建部门',
                                     create_by                    bigint(0)     DEFAULT NULL                            COMMENT '创建人',
                                     update_time                  datetime(0)   DEFAULT NULL                            COMMENT '更新时间',
                                     update_by                    bigint(0)     DEFAULT NULL                            COMMENT '更新人',
                                     del_flag                     int(0)        DEFAULT 0                               COMMENT '删除标志',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     KEY `idx_link_file_id` (`link_file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='文件历史';


-- 文件分享
DROP TABLE IF EXISTS `kb_file_share`;
CREATE TABLE `kb_file_share`  (
                                     id                           bigint        NOT NULL                                COMMENT '主键',
                                     link_user_id                 bigint        NULL                                    COMMENT '用户ID',
                                     link_user_name               varchar(64)   NULL                                    COMMENT '用户名称',
                                     link_file_id                 bigint        NOT NULL                                COMMENT '文件ID',
                                     link_file_content_id         bigint        NULL                                    COMMENT '文件内容ID',
                                     share_code                   varchar(64)   NULL                                    COMMENT '分享ID',

                                     tenant_id                    varchar(20)   DEFAULT '000000'                        COMMENT '租户ID',
                                     pic_url                      varchar(255)  DEFAULT ''                              COMMENT '图片地址',
                                     create_time                  datetime(0)   DEFAULT NULL                            COMMENT '创建时间',
                                     create_dept                  bigint(20)    DEFAULT NULL                            COMMENT '创建部门',
                                     create_by                    bigint(0)     DEFAULT NULL                            COMMENT '创建人',
                                     update_time                  datetime(0)   DEFAULT NULL                            COMMENT '更新时间',
                                     update_by                    bigint(0)     DEFAULT NULL                            COMMENT '更新人',
                                     del_flag                     int(0)        DEFAULT 0                               COMMENT '删除标志',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     KEY `idx_link_file_id` (`link_file_id`) USING BTREE,
                                     KEY `idx_share_code` (`share_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='文件分享';



-- 文档模板类型
DROP TABLE IF EXISTS `kb_file_template_type`;
CREATE TABLE `kb_file_template_type`  (
                                           id                           bigint        NOT NULL                               COMMENT '主键',
                                           name                         varchar(64)   NULL                                   COMMENT '名称',
                                           sort                         int(10)       NULL DEFAULT NULL                      COMMENT '排序',

                                           tenant_id                    varchar(20)   DEFAULT '000000'                       COMMENT '租户ID',
                                           pic_url                      varchar(255)  DEFAULT ''                             COMMENT '图片地址',
                                           create_time                  datetime(0)   DEFAULT NULL                           COMMENT '创建时间',
                                           create_dept                  bigint(20)    DEFAULT NULL                           COMMENT '创建部门',
                                           create_by                    bigint(0)     DEFAULT NULL                           COMMENT '创建人',
                                           update_time                  datetime(0)   DEFAULT NULL                           COMMENT '更新时间',
                                           update_by                    bigint(0)     DEFAULT NULL                           COMMENT '更新人',
                                           del_flag                     int(0)        DEFAULT 0                              COMMENT '删除标志',
                                           PRIMARY KEY (`id`) USING BTREE,
                                           KEY `idx_name` (`name`) USING BTREE,
                                           KEY `idx_sort` (`sort`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='文档模板类型';

-- 文档模板
DROP TABLE IF EXISTS `kb_file_template`;
CREATE TABLE `kb_file_template`  (
                                           id                           bigint        NOT NULL                               COMMENT '主键',
                                           name                         varchar(64)   NOT NULL                               COMMENT '模板名称',
                                           file_type_code               varchar(64)   NULL                                   COMMENT '文件类型',
                                           file_type_name               varchar(64)   NULL                                   COMMENT '文件类型名称',
                                           use_count                    int           DEFAULT 1                              COMMENT '引用次数',
                                           status                       int           NULL DEFAULT NULL                      COMMENT '状态',
                                           link_file_template_type_id   bigint        NOT NULL                               COMMENT '模板类型ID',
                                           attr_type                    varchar(10)   NULL                                   COMMENT '后缀名',
                                           attr_content                 longblob      NULL                                   COMMENT '内容',

                                           tenant_id                    varchar(20)   DEFAULT '000000'                       COMMENT '租户ID',
                                           pic_url                      varchar(255)  DEFAULT ''                             COMMENT '图片地址',
                                           create_time                  datetime(0)   DEFAULT NULL                           COMMENT '创建时间',
                                           create_dept                  bigint(20)    DEFAULT NULL                           COMMENT '创建部门',
                                           create_by                    bigint(0)     DEFAULT NULL                           COMMENT '创建人',
                                           update_time                  datetime(0)   DEFAULT NULL                           COMMENT '更新时间',
                                           update_by                    bigint(0)     DEFAULT NULL                           COMMENT '更新人',
                                           del_flag                     int(0)        DEFAULT 0                              COMMENT '删除标志',
                                           PRIMARY KEY (`id`) USING BTREE,
                                           KEY `idx_name` (`name`) USING BTREE,
                                           KEY `idx_file_type_code` (`file_type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='文档模板';


-- 回收站
DROP TABLE IF EXISTS `kb_recycle`;
CREATE TABLE `kb_recycle`  (
                                 id                           bigint          NOT NULL                            COMMENT '主键',
                                 type                         varchar(10)     NULL                                COMMENT '删除对象类型',
                                 link_id                      bigint          NOT NULL                            COMMENT '删除对象ID',
                                 link_name                    varchar(64)     NULL                                COMMENT '删除对象名称',
                                 completely_del_time          datetime(0)     NULL DEFAULT NULL                   COMMENT '彻底删除时间',

                                 link_km_id                   bigint          NOT NULL                                COMMENT '知识库ID',
                                 link_file_type_code          varchar(64)     NULL                                    COMMENT '文件类型ID',
                                 link_file_type_name          varchar(64)     NULL                                    COMMENT '文件类型名称',

                                 tenant_id                    varchar(20)     DEFAULT '000000'                    COMMENT '租户ID',
                                 pic_url                      varchar(255)    DEFAULT ''                          COMMENT '图片地址',
                                 create_time                  datetime(0)     DEFAULT NULL                        COMMENT '创建时间',
                                 create_dept                  bigint(20)      DEFAULT NULL                        COMMENT '创建部门',
                                 create_by                    bigint(0)       DEFAULT NULL                        COMMENT '创建人',
                                 update_time                  datetime(0)     DEFAULT NULL                        COMMENT '更新时间',
                                 update_by                    bigint(0)       DEFAULT NULL                        COMMENT '更新人',
                                 del_flag                     int(0)          DEFAULT 0                           COMMENT '删除标志',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='回收站';


-- 搜索历史
DROP TABLE IF EXISTS `kb_search_history`;
CREATE TABLE `kb_search_history`  (
                               id                           bigint          NOT NULL                            COMMENT '主键',
                               keyword                      varchar(128)    NOT NULL                            COMMENT '搜索关键字',
                               count                        int             DEFAULT 0                           COMMENT '搜索次数',

                               tenant_id                    varchar(20)     DEFAULT '000000'                    COMMENT '租户ID',
                               pic_url                      varchar(255)    DEFAULT ''                          COMMENT '图片地址',
                               create_time                  datetime(0)     DEFAULT NULL                        COMMENT '创建时间',
                               create_dept                  bigint(20)      DEFAULT NULL                        COMMENT '创建部门',
                               create_by                    bigint(0)       DEFAULT NULL                        COMMENT '创建人',
                               update_time                  datetime(0)     DEFAULT NULL                        COMMENT '更新时间',
                               update_by                    bigint(0)       DEFAULT NULL                        COMMENT '更新人',
                               del_flag                     int(0)          DEFAULT 0                           COMMENT '删除标志',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `idx_keyword` (`keyword`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='搜索历史';



-- 集成存储配置
DROP TABLE IF EXISTS `kb_storage_config`;
CREATE TABLE `kb_storage_config`  (
                                    id                           bigint          NOT NULL                          COMMENT '主键',
                                    name                         varchar(64)     NULL                              COMMENT '名称',
                                    link_team_id                 bigint          NOT NULL                          COMMENT '团队成员ID',
                                    config_json                  varchar(20000)  NULL                              COMMENT '配置信息JSON',

                                    tenant_id                    varchar(20)     DEFAULT '000000'                  COMMENT '租户ID',
                                    pic_url                      varchar(255)    DEFAULT ''                        COMMENT '图片地址',
                                    create_time                  datetime(0)     DEFAULT NULL                      COMMENT '创建时间',
                                    create_dept                  bigint(20)      DEFAULT NULL                      COMMENT '创建部门',
                                    create_by                    bigint(0)       DEFAULT NULL                      COMMENT '创建人',
                                    update_time                  datetime(0)     DEFAULT NULL                      COMMENT '更新时间',
                                    update_by                    bigint(0)       DEFAULT NULL                      COMMENT '更新人',
                                    del_flag                     int(0)          DEFAULT 0                         COMMENT '删除标志',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    KEY `idx_name` (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment='集成存储配置';



