-- ### 主模块: 共通功能 DB设计 ####
-- ### 前缀：cv_
-- ### 版本：V1.0
-- ### 作者：Jack.Liao
-- #######################

-- 简历订单(字典配置：cv_)
DROP TABLE IF EXISTS `cv_order`;
CREATE TABLE `cv_order` (
                            `id`                bigint(20)      NOT NULL                COMMENT 'ID',
                            `order_number`      varchar(20)     NOT NULL                COMMENT '订单编号',
                            `recruiter`         varchar(20)     NOT NULL                COMMENT '招募官',
                            `referrer`          varchar(20)     NOT NULL                COMMENT '推荐官',
                            `order_status`      int(1)          DEFAULT '1'             COMMENT '订单状态',
                            `link_contract_id`  bigint(20)      DEFAULT NULL            COMMENT '关联合同ID',
                            `link_customer_id`  bigint(20)      NOT NULL                COMMENT '关联客户ID',
                            `link_job_id`       bigint(20)      NOT NULL                COMMENT '关联职业ID',
                            `tenant_id`         varchar(20)     DEFAULT '000000'        COMMENT '租户ID',
                            `pic_url`           varchar(255)    DEFAULT ''              COMMENT '图片地址',
                            `create_time`       datetime        DEFAULT NULL            COMMENT '创建时间',
                            `create_dept`       bigint(20)      DEFAULT NULL            COMMENT '创建部门',
                            `create_by`         bigint(20)      DEFAULT NULL            COMMENT '创建人',
                            `update_time`       datetime        DEFAULT NULL            COMMENT '更新时间',
                            `update_by`         bigint(20)      DEFAULT NULL            COMMENT '更新人',
                            `del_flag`          INT(1)          DEFAULT '0'             COMMENT '删除标志(0：未删除，2：已删除)',
                            PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '简历订单';


DROP TABLE IF EXISTS `cv_customer`;
CREATE TABLE `cv_customer` (
                               id                           bigint      NOT NULL                                COMMENT 'ID',
                               link_organization_id         bigint(20)  NOT NULL                          COMMENT '所属机构id',
                               customer_name                varchar(255) NOT NULL                               COMMENT '客户名称',
                               customer_code                varchar(255) NOT NULL                               COMMENT '客户CODE',
                               remark                       varchar(255) DEFAULT NULL                           COMMENT '备注',

                               pic_url                      varchar(255) DEFAULT ''                             COMMENT '图片地址',
                               tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                               create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                               create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                               create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                               update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                               update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                               del_flag                     int(1)       DEFAULT '0'                            COMMENT '删除标志(0：未删除，2：已删除)',

                               PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '简历客户';

DROP TABLE IF EXISTS `cv_job`;
CREATE TABLE `cv_job` (
                          id                            bigint          NOT NULL                               COMMENT 'ID',
                          link_customer_id              bigint(20)      NOT NULL                               COMMENT '客户ID',
                          link_job_template_id          bigint(20)      NOT NULL                               COMMENT '职位模板ID',
                          job_title                     varchar(255)    NOT NULL                               COMMENT '职位名称',
                          start_date                    DATE            DEFAULT NULL                           COMMENT '开始日期',
                          end_date                      DATE            DEFAULT NULL                           COMMENT '结束日期',
                          work_location                 varchar(255)    DEFAULT NULL                           COMMENT '工作地点',
                          course_type                   varchar(255)    DEFAULT NULL                           COMMENT '课程体系',
                          language_requirement          varchar(255)    DEFAULT NULL                           COMMENT '语言要求',
                          education_level               varchar(255)    DEFAULT NULL                           COMMENT '学历要求',
                          teaching_subject              varchar(255)    DEFAULT NULL                           COMMENT '教学科目',
                          salary_min                    DECIMAL(10,2)   DEFAULT NULL                           COMMENT '薪酬范围下限',
                          salary_max                    DECIMAL(10,2)   DEFAULT NULL                           COMMENT '薪酬范围上限',
                          job_description               TEXT            DEFAULT NULL                           COMMENT '职位描述',
                          file_oss_id                   varchar(256)    DEFAULT NULL                           COMMENT '文件ossId',
                          tenant_id                     varchar(20)     NULL DEFAULT '000000'                  COMMENT '租户ID',
                          create_time                   datetime        NULL DEFAULT NULL                      COMMENT '创建时间',
                          create_dept                   bigint(20)      NULL DEFAULT NULL                      COMMENT '创建部门',

                          create_by                     bigint(20)      NULL DEFAULT NULL                      COMMENT '创建人',
                          update_time                   datetime        NULL DEFAULT NULL                      COMMENT '更新时间',
                          update_by                     bigint(20)      NULL DEFAULT NULL                      COMMENT '更新人',
                          del_flag                      int(1)          DEFAULT '0'                            COMMENT '删除标志(0：未删除，2：已删除)',

                          PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '简历职位信息表'


DROP TABLE IF EXISTS `cv_organization`;
CREATE TABLE `cv_organization` (
                                   id                           bigint       NOT NULL                               COMMENT 'ID',
                                   organization_name            varchar(255) NOT NULL                               COMMENT '组织名称',
                                   description                  varchar(255) NOT NULL                               COMMENT '组织描述',
                                   parent_id                    bigint(20)   DEFAULT '0'                            COMMENT '父级组织ID',
                                   tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                   create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                   create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',

                                   create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                   update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                   update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                   del_flag                     int(1)       DEFAULT '0'                            COMMENT '删除标志(0：未删除，2：已删除)',

                                   PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '简历组织架构表';


DROP TABLE IF EXISTS `cv_job_template`;
CREATE TABLE `cv_job_template` (
                                   id                        bigint       NOT NULL                    COMMENT 'ID',
                                   template_name             varchar(128) NULL DEFAULT NULL           COMMENT '职位模板名称',
                                   job_template_description  TEXT         DEFAULT NULL                COMMENT '职位模板描述',

                                   tenant_id                 varchar(20)  NULL DEFAULT '000000'       COMMENT '租户ID',
                                   create_time               datetime     NULL DEFAULT NULL           COMMENT '创建时间',
                                   create_dept               bigint(20)   NULL DEFAULT NULL           COMMENT '创建部门',
                                   create_by                 bigint(20)   NULL DEFAULT NULL           COMMENT '创建人',
                                   update_time               datetime     NULL DEFAULT NULL           COMMENT '更新时间',
                                   update_by                 bigint(20)   NULL DEFAULT NULL           COMMENT '更新人',
                                   del_flag                  int(1)       DEFAULT '0'                 COMMENT '删除标志(0：未删除，2：已删除)',

                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '简历职位模板表';

