-- ### 主模块: 共通功能 DB设计 ####
-- ### 前缀：pay_
-- ### 版本：V1.0
-- ### 作者：Moks、Spark
-- #######################

-- 支付渠道(字典配置：com_pay_way)

-- 支付订单
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order`  (
                                  id                           bigint       NOT NULL                               COMMENT 'ID',
                                  order_no                     bigint       NOT NULL                               COMMENT '订单编号',
                                  subject                      varchar(64)  NOT NULL                               COMMENT '商品标题',
                                  amount                       decimal      NOT NULL                               COMMENT '金额',
                                  pay_way_code                 varchar(64)  NOT NULL                               COMMENT '支付渠道编码',
                                  status                       int          NULL DEFAULT 0                         COMMENT '支付状态 1：支付成功 0:待支付 2: 支付关闭',

                                  tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                  pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                  create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                  create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                  create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                  update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                  update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                  del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                  PRIMARY KEY (`id`) USING BTREE,
                                  KEY `idx_subject` (`subject`) USING BTREE,
                                  KEY `idx_pay_way_code` (`pay_way_code`) USING BTREE,
                                  KEY `idx_status` (`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment ='支付订单';

-- 支付回调
DROP TABLE IF EXISTS `pay_callback`;
CREATE TABLE `pay_callback`  (
                                 id                           bigint       NOT NULL                               COMMENT 'ID',
                                 order_no                     bigint       NOT NULL                               COMMENT '订单编号',
                                 status                       int          NOT NULL                               COMMENT '通知状态：等待通知、通知成功、通知失败、其他',
                                 count                        int          NULL DEFAULT 0                         COMMENT '通知次数',
                                 rt_params                    longtext     NUll                                   COMMENT '支付回调参数',

                                 tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                 pic_url                      varchar(255) NULL DEFAULT ''                        COMMENT '图片地址',
                                 create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                 create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                 create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                 update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                 update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                 del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `idx_order_no` (`order_no`) USING BTREE,
                                 KEY `idx_status` (`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment ='支付回调';


