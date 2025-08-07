-- ### 主模块: 共通功能 DB设计 ####
-- ### 前缀：cv_
-- ### 版本：V1.0
-- ### 作者：Moks.Mo
-- #######################


DROP TABLE IF EXISTS `cv_order_candidates`;
CREATE TABLE `cv_order_candidates` (
                            `id`                BIGINT ( 20 ) NOT NULL COMMENT 'ID',
                            `link_order_id`     BIGINT ( 20 ) NOT NULL COMMENT '关联订单ID',
                            `link_cv_id`        VARCHAR ( 30 ) NOT NULL COMMENT '关联客户ID',
                            `link_cv_name`      VARCHAR ( 30 ) NOT NULL COMMENT '关联候选人名称',
                            `status`            VARCHAR ( 20 ) NOT NULL COMMENT '状态',
                            `remark`            VARCHAR ( 1024 ) NOT NULL COMMENT '备注',

                            `tenant_id`         VARCHAR ( 20 ) DEFAULT '000000' COMMENT '租户ID',
                            `pic_url`           VARCHAR ( 255 ) DEFAULT '' COMMENT '图片地址',
                            `create_time`       datetime DEFAULT NULL COMMENT '创建时间',
                            `create_dept`       BIGINT ( 20 ) DEFAULT NULL COMMENT '创建部门',
                            `create_by`         BIGINT ( 20 ) DEFAULT NULL COMMENT '创建人',
                            `update_time`       datetime DEFAULT NULL COMMENT '更新时间',
                            `update_by`         BIGINT ( 20 ) DEFAULT NULL COMMENT '更新人',
                            `del_flag`          INT ( 1 ) DEFAULT '0' COMMENT '删除标志(0：未删除，2：已删除)',
                            PRIMARY KEY ( `id` ) USING BTREE,
                            KEY `idx_link_order_id` (`link_order_id`) USING BTREE,
                            KEY `idx_link_cv_id` (`link_cv_id`) USING BTREE,
                            KEY `idx_status` (`status`) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '备选列表';

DROP TABLE IF EXISTS `cv_order_offer`;
CREATE TABLE `cv_order_offer` (
                                       `id`                BIGINT ( 20 )  NOT NULL COMMENT 'ID',
                                       `link_order_id`     BIGINT ( 20 ) NOT NULL COMMENT '关联订单ID',
                                       `link_cv_id`        VARCHAR ( 30 ) NOT NULL COMMENT '关联简历ID',
                                       `link_cv_name`      VARCHAR ( 36 ) NULL COMMENT '关联简历名称',
                                       `salary`            float(10,2) DEFAULT 0 COMMENT '薪资',
                                       `contract_date`     VARCHAR ( 20 ) DEFAULT NULL COMMENT '合同期',
                                       `effective_date`    datetime DEFAULT NULL COMMENT '生效日期',
                                       `status`            VARCHAR ( 20 ) NOT NULL COMMENT '状态',

                                       `tenant_id`         VARCHAR ( 20 ) DEFAULT '000000' COMMENT '租户ID',
                                       `pic_url`           VARCHAR ( 255 ) DEFAULT '' COMMENT '图片地址',
                                       `create_time`       datetime DEFAULT NULL COMMENT '创建时间',
                                       `create_dept`       BIGINT ( 20 ) DEFAULT NULL COMMENT '创建部门',
                                       `create_by`         BIGINT ( 20 ) DEFAULT NULL COMMENT '创建人',
                                       `update_time`       datetime DEFAULT NULL COMMENT '更新时间',
                                       `update_by`         BIGINT ( 20 ) DEFAULT NULL COMMENT '更新人',
                                       `del_flag`          INT ( 1 ) DEFAULT '0' COMMENT '删除标志(0：未删除，2：已删除)',
                                       PRIMARY KEY ( `id` ) USING BTREE,
                                       KEY `idx_link_order_id` (`link_order_id`) USING BTREE,
                                       KEY `idx_link_cv_id` (`link_cv_id`) USING BTREE,
                                       KEY `idx_status` (`status`) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = 'OFFER';

DROP TABLE IF EXISTS `cv_contract`;
CREATE TABLE `cv_contract` (
                                  `id`                BIGINT ( 20 )  NOT NULL COMMENT 'ID',
                                  `contract_num`      BIGINT ( 20 ) NOT NULL COMMENT '合同编号',
                                  `link_customer_id`  BIGINT ( 20 ) NOT NULL COMMENT '关联客户ID',
                                  `contract_date`     VARCHAR ( 20 ) DEFAULT NULL COMMENT '签约日期',
                                  `effective_date`    datetime DEFAULT NULL COMMENT '生效日期',
                                  `status`            VARCHAR ( 20 ) NOT NULL COMMENT '状态',
                                  `contractor`        VARCHAR ( 36 ) NOT NULL COMMENT '联系人',
                                  `contractor_phone`  VARCHAR ( 36 ) NOT NULL COMMENT '联系电话',

                                  `tenant_id`         VARCHAR ( 20 ) DEFAULT '000000' COMMENT '租户ID',
                                  `pic_url`           VARCHAR ( 255 ) DEFAULT '' COMMENT '图片地址',
                                  `create_time`       datetime DEFAULT NULL COMMENT '创建时间',
                                  `create_dept`       BIGINT ( 20 ) DEFAULT NULL COMMENT '创建部门',
                                  `create_by`         BIGINT ( 20 ) DEFAULT NULL COMMENT '创建人',
                                  `update_time`       datetime DEFAULT NULL COMMENT '更新时间',
                                  `update_by`         BIGINT ( 20 ) DEFAULT NULL COMMENT '更新人',
                                  `del_flag`          INT ( 1 ) DEFAULT '0' COMMENT '删除标志(0：未删除，2：已删除)',
                                  PRIMARY KEY ( `id` ) USING BTREE,
                                  KEY `idx_contract_num` (`contract_num`) USING BTREE,
                                  KEY `idx_link_customer_id` (`link_customer_id`) USING BTREE,
                                  KEY `idx_status` (`status`) USING BTREE,
                                  KEY `idx_contractor` (`contractor`) USING BTREE,
                                  KEY `idx_contractor_phone` (`contractor_phone`) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '合同';


DROP TABLE IF EXISTS `cv_payment`;
CREATE TABLE `cv_payment` (
                               `id`                BIGINT ( 20 )  NOT NULL COMMENT 'ID',
                               `link_contract_num` BIGINT ( 20 )  NOT NULL COMMENT '关联合同编号',
                               `link_customer_id`  BIGINT ( 20 )  NOT NULL COMMENT '关联客户ID',
                               `pay_amount`        float(10,2)    DEFAULT NULL COMMENT '付款金额',
                               `total_amount`      float(10,2)    DEFAULT NULL COMMENT '总金额',
                               `invoice_status`    INT ( 1 )      DEFAULT '0' COMMENT '是否已开票',
                               `status`            VARCHAR ( 20 ) NOT NULL COMMENT '状态',

                               `tenant_id`         VARCHAR ( 20 ) DEFAULT '000000' COMMENT '租户ID',
                               `pic_url`           VARCHAR ( 255 ) DEFAULT '' COMMENT '图片地址',
                               `create_time`       datetime DEFAULT NULL COMMENT '创建时间',
                               `create_dept`       BIGINT ( 20 ) DEFAULT NULL COMMENT '创建部门',
                               `create_by`         BIGINT ( 20 ) DEFAULT NULL COMMENT '创建人',
                               `update_time`       datetime DEFAULT NULL COMMENT '更新时间',
                               `update_by`         BIGINT ( 20 ) DEFAULT NULL COMMENT '更新人',
                               `del_flag`          INT ( 1 ) DEFAULT '0' COMMENT '删除标志(0：未删除，2：已删除)',
                               PRIMARY KEY ( `id` ) USING BTREE,
                               KEY `idx_link_contract_num` (`link_contract_num`) USING BTREE,
                               KEY `idx_link_customer_id` (`link_customer_id`) USING BTREE,
                               KEY `idx_invoice_status` (`invoice_status`) USING BTREE,
                               KEY `idx_status` (`status`) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '付款';



DROP TABLE IF EXISTS `cv_recommend_record`;
CREATE TABLE `cv_recommend_record` (
                              `id`                BIGINT ( 20 )  NOT NULL COMMENT 'ID',
                              `link_order_id`     BIGINT ( 20 ) NOT NULL COMMENT '关联订单ID',
                              `link_cv_ids`       VARCHAR ( 2048 )  NOT NULL COMMENT '关联简历编号',
                              `message`           VARCHAR ( 2048 )  NOT NULL COMMENT '消息',

                              `tenant_id`         VARCHAR ( 20 ) DEFAULT '000000' COMMENT '租户ID',
                              `pic_url`           VARCHAR ( 255 ) DEFAULT '' COMMENT '图片地址',
                              `create_time`       datetime DEFAULT NULL COMMENT '创建时间',
                              `create_dept`       BIGINT ( 20 ) DEFAULT NULL COMMENT '创建部门',
                              `create_by`         BIGINT ( 20 ) DEFAULT NULL COMMENT '创建人',
                              `update_time`       datetime DEFAULT NULL COMMENT '更新时间',
                              `update_by`         BIGINT ( 20 ) DEFAULT NULL COMMENT '更新人',
                              `del_flag`          INT ( 1 ) DEFAULT '0' COMMENT '删除标志(0：未删除，2：已删除)',
                              PRIMARY KEY ( `id` ) USING BTREE,
                              KEY `idx_link_order_id` (`link_order_id`) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '推荐历史';


