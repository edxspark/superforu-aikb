-- ### 主模块: 共通功能 DB设计 ####
-- ### 前缀：com_
-- ### 版本：V1.0
-- ### 作者：Moks、Spark
-- #######################

-- ## 用户管理
-- 用户信息
DROP TABLE IF EXISTS `com_user`;
CREATE TABLE `com_user`  (
                           id                           bigint       NOT NULL                               COMMENT 'ID',
                           user_account                 varchar(64)  NOT NULL                               COMMENT '账号',
                           user_name                    varchar(64)  NULL                                   COMMENT '名称',
                           user_psw                     varchar(64)  NULL                                   COMMENT '密码',
                           signature                    varchar(255) NULL                                   COMMENT '签名',

                           tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                           pic_url                      varchar(255) NULL DEFAULT NULL                      COMMENT '图片地址',
                           theme                        varchar(20)  NOT NULL                               COMMENT '主题类型（dark: 炫黑，light: 亮白）',
                           language                     varchar(20)  NOT NULL                               COMMENT 'zh-CN：中文，en-US：英文',

                           create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                           create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',

                           create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                           update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                           update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                           del_flag                     int(1)       DEFAULT '0'                            COMMENT '删除标志(0：未删除，2：已删除)',

                           PRIMARY KEY (`id`) USING BTREE,
                           KEY `idx_user_account` (`user_account`) USING BTREE,
                           KEY `idx_user_name` (`user_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='用户信息表';



-- 邀请记录
DROP TABLE IF EXISTS `com_invite_history`;
CREATE TABLE `com_invite_history`  (
                                          id                           bigint       NOT NULL                               COMMENT '主键',
                                          link_inviter_id              bigint(20)   NOT NULL                               COMMENT '邀请者用户id',
                                          link_invitee_id              bigint(20)   NOT NULL                               COMMENT '被邀请者用户id',
                                          get_detail                   varchar(255) NULL DEFAULT ''                        COMMENT '获得权益',

                                          tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                          create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                          create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',

                                          create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                          update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                          update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                          del_flag                     int(1)       DEFAULT '0'                            COMMENT '删除标志(0：未删除，2：已删除)',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='邀请记录';

-- ## 团队管理
-- 团队管理
DROP TABLE IF EXISTS `com_team`;
CREATE TABLE `com_team`  (
                                id                           bigint       NOT NULL                               COMMENT '主键',
                                team_name                    varchar(64)  NOT NULL                               COMMENT '团队名称',
                                team_desc                    varchar(255) NULL DEFAULT NULL                      COMMENT '团队描述',

                                tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                pic_url                      varchar(255) NULL DEFAULT NULL                      COMMENT '图片地址',
                                create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',

                                create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                del_flag                     int(1)       DEFAULT '0'                            COMMENT '删除标志(0：未删除，2：已删除)',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='团队管理表';


-- 团队成员管理
DROP TABLE IF EXISTS `com_team_mate`;
CREATE TABLE `com_team_mate`  (
                                id                           bigint       NOT NULL                               COMMENT '主键',
                                role_type                    int(1)       NOT NULL                               COMMENT '角色类型（0：查看者，1：编辑者，2：管理员）',
                                link_team_id                 bigint(20)   NOT NULL                               COMMENT '团队id',
                                link_user_id                 bigint(20)   NOT NULL                               COMMENT '用户id',

                                tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',

                                create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                del_flag                     int(1)       DEFAULT '0'                            COMMENT '删除标志(0：未删除，2：已删除)',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='团队成员管理表';


-- 用户配置
DROP TABLE IF EXISTS `com_user_config`;
CREATE TABLE `com_user_config`  (
                              id                           bigint       NOT NULL                               COMMENT 'ID',
                              link_user_id                 bigint       NULL                                   COMMENT '用户ID',
                              type                         int(1)       NOT NULL                               COMMENT '配置类型（0：快捷入口、1：最近访问:2：超级模块）',
                              config_values                varchar(999) NULL                                   COMMENT '配置内容JSON',

                              tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                              create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                              create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',

                              create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                              update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                              update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                              del_flag                     int(1)       DEFAULT '0'                            COMMENT '删除标志(0：未删除，2：已删除)',

                              PRIMARY KEY (`id`) USING BTREE,
                              KEY `idx_type` (`type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='用户配置表';


-- 用户权益套餐配置表
DROP TABLE IF EXISTS `com_user_equity`;
CREATE TABLE `com_user_equity`  (
                                    id                           bigint       NOT NULL                               COMMENT 'ID',
                                    name                         varchar(64)  NOT NULL                               COMMENT '套餐名称',
                                    level                        int(1)       NOT NULL                               COMMENT '等级（0：普通会员、1：专业会员、2：超级会员）',
                                    config_values                varchar(999) NULL                                   COMMENT '配置内容JSON',

                                    tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                    pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                    create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                    create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',

                                    create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                    update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                    update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                    del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                    PRIMARY KEY (`id`) USING BTREE,
                                    KEY `idx_name` (`name`) USING BTREE,
                                    KEY `idx_level` (`level`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='用户权益套餐配置表';



-- 用户套餐项目(字典配置:com_user_package)

-- 购买:用户套餐购买详细
DROP TABLE IF EXISTS `com_user_package_purchase`;
CREATE TABLE `com_user_package_purchase`  (
                                    id                           bigint       NOT NULL                               COMMENT 'ID',
                                    link_user_id                 bigint       NULL                                   COMMENT '用户ID',
                                    package_code                 varchar(24)  NOT NULL                               COMMENT '编码',
                                    package_name                 varchar(36)  NOT NULL                               COMMENT '名称',
                                    convert_util                 int          NULL DEFAULT 1                         COMMENT '换算单位',
                                    unit                         varchar(12)  NULL                                   COMMENT '单位',
                                    price                        float(2)     NULL                                   COMMENT '价格',
                                    number                       int          NULL                                   COMMENT '数量',
                                    total                        float(2)     NULL                                   COMMENT '总价',
                                    promotion_code               varchar(6)   NOT NULL DEFAULT ''                    COMMENT '优惠码',
                                    promotion_value              int          NOT NULL DEFAULT 0                     COMMENT '优惠金额',
                                    pay_way                      varchar(12)  NOT NULL                               COMMENT '支付渠道',
                                    pay_no                       varchar(36)  NULL                                   COMMENT '支付单号',
                                    remark                       varchar(255) NULL                                   COMMENT '备注',

                                    tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                    pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                    create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                    create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                    create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                    update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                    update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                    del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                    PRIMARY KEY (`id`) USING BTREE,
                                    KEY `idx_package_code` (`package_code`) USING BTREE,
                                    KEY `idx_package_name` (`package_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='用户套餐购买详细';


-- 拥有:用户套餐明细
DROP TABLE IF EXISTS `com_user_package_detail`;
CREATE TABLE `com_user_package_detail`  (
                                    id                           bigint       NOT NULL                               COMMENT 'ID',
                                    link_user_id                 bigint       NULL                                   COMMENT '用户ID',
                                    package_code                 varchar(24)  NOT NULL                               COMMENT '编码',
                                    package_name                 varchar(36)  NOT NULL                               COMMENT '名称',
                                    convert_util                 int          NULL DEFAULT 1                         COMMENT '换算单位',
                                    convert                      int          NULL DEFAULT 1                         COMMENT '换算',
                                    value                        float(2)     NULL                                   COMMENT '数量',
                                    app_value                    varchar(99)  NULL                                   COMMENT '应用使用值',

                                    tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                    pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                    create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                    create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                    create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                    update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                    update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                    del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                    PRIMARY KEY (`id`) USING BTREE,
                                    KEY `idx_package_code` (`package_code`) USING BTREE,
                                    KEY `idx_package_name` (`package_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='用户权益资源套餐明细';

-- 消费:用户充值消费明细
DROP TABLE IF EXISTS `com_user_package_use_detail`;
CREATE TABLE `com_user_package_use_detail`  (
                                      id                           bigint       NOT NULL                               COMMENT 'ID',
                                      link_user_id                 bigint       NULL                                   COMMENT '用户ID',
                                      package_code                 varchar(24)  NOT NULL                               COMMENT '编码',
                                      package_name                 varchar(36)  NOT NULL                               COMMENT '名称',
                                      unit                         varchar(12)  NULL                                   COMMENT '单位',
                                      number                       float(2)     NULL                                   COMMENT '本次消费数量',
                                      balance_before                float(2)     NULL                                   COMMENT '消费前结余',
                                      balance_after                 float(2)     NULL                                   COMMENT '消费后结余',

                                      tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                      pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                      create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                      create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                      create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                      update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                      update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                      del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                      PRIMARY KEY (`id`) USING BTREE,
                                      KEY `idx_package_code` (`package_code`) USING BTREE,
                                      KEY `idx_package_name` (`package_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='用户充值消费明细';


-- 优惠码
DROP TABLE IF EXISTS `com_user_package_promotion`;
CREATE TABLE `com_user_package_promotion`  (
                                                id                           bigint       NOT NULL                               COMMENT 'ID',
                                                promotion_code               varchar(6)   NOT NULL DEFAULT ''                    COMMENT '优惠码',
                                                promotion_value              int          NOT NULL DEFAULT 1                     COMMENT '优惠金额',
                                                max_use_count                int          NOT NULL DEFAULT 1                     COMMENT '最大使用次数',
                                                used_count                   int          NOT NULL DEFAULT 0                     COMMENT '已经使用次数',

                                                tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                                pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                                create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                                create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                                create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                                update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                                update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                                del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                                PRIMARY KEY (`id`) USING BTREE,
                                                KEY `idx_promotion_code` (`promotion_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='优惠码';




-- ## 应用配置
-- 菜单配置
DROP TABLE IF EXISTS `com_menu_config`;
CREATE TABLE `com_menu_config`  (
                                       id                           bigint       NOT NULL                               COMMENT 'ID',
                                       name                         varchar(64)  NOT NULL                               COMMENT '名称',
                                       sort                         int(0)       NULL DEFAULT 0                         COMMENT '排序',
                                       status                       int          NULL DEFAULT NULL                      COMMENT '状态 1：启用 0:待启用',
                                       value                       varchar(999)  NULL                                   COMMENT '配置内容',

                                       tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                       pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                       create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                       create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',

                                       create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                       update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                       update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                       del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                       PRIMARY KEY (`id`) USING BTREE,
                                       KEY `idx_name` (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='菜单配置表';


-- 超级模块配置
DROP TABLE IF EXISTS `com_super_module_config`;
CREATE TABLE `com_super_module_config`  (
                                       id                           bigint       NOT NULL                               COMMENT 'ID',
                                       name                         varchar(64)  NOT NULL                               COMMENT '名称',
                                       sort                         int(0)       NULL DEFAULT 0                         COMMENT '排序',
                                       link_user_equity_id          bigint(20)   NOT NULL                               COMMENT '用户等级id关联',
                                       status                       int          NULL DEFAULT NULL                      COMMENT '是否初始化（0：初始化 1:未初始化）',
                                       value                        varchar(999) NULL                                   COMMENT '配置内容',
                                       icon                         varchar(24)  NOT NULL                               COMMENT '模块ICON',
                                       color                        varchar(24)  NOT NULL                               COMMENT '模块颜色',
                                       open_way                     varchar(10)  NOT NULL DEFAULT '_self'               COMMENT '打开方式',

                                       tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                       create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                       create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                       create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                       update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                       update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                       del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                       PRIMARY KEY (`id`) USING BTREE,
                                       KEY `idx_name` (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='超级模块配置表';



-- 快捷入口配置
DROP TABLE IF EXISTS `com_quick_entrance_config`;
CREATE TABLE `com_quick_entrance_config`  (
                                            id                           bigint       NOT NULL                               COMMENT 'ID',
                                            sort                         int(0)       NULL DEFAULT 0                         COMMENT '排序',
                                            link_file_type_id            bigint(20)   NULL DEFAULT NULL                      COMMENT '文件类型id关联',
                                            status                       int          NULL DEFAULT NULL                      COMMENT '是否初始化（0：初始化 1:未初始化）',

                                            tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                            create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                            create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                            create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                            update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                            update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                            del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='快捷入口配置';

-- ## 消息中心
-- 消息中心
DROP TABLE IF EXISTS `com_msg_center`;
CREATE TABLE `com_msg_center`  (
                                               id                           bigint       NOT NULL                               COMMENT 'ID',
                                               type                         varchar(64)  NULL                                   COMMENT '类型：wechat:微信通知、msg:短信通知等',
                                               user_id                      bigint       NULL                                   COMMENT '用户ID',
                                               user_name                    varchar(64)  NULL                                   COMMENT '用户名称',
                                               status                       int          NULL DEFAULT NULL                      COMMENT '状态 1：已发送 0:待发送 2:发送失败',
                                               value                        varchar(999) NULL                                   COMMENT '配置内容',

                                               tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                               pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                               create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                               create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',

                                               create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                               update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                               update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                               del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                               PRIMARY KEY (`id`) USING BTREE,
                                               KEY `idx_user_name` (`user_name`) USING BTREE,
                                               KEY `idx_status` (`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment = '消息中心';


