-- ### 主模块: 人脸功能 DB设计 ####
-- ### 前缀：face_
-- ### 版本：V1.0
-- ### 作者：Moks.Mo
-- #######################

-- 人脸识别(字典配置：face_)

-- 人脸识别库
DROP TABLE IF EXISTS `face_subject`;
CREATE TABLE `face_subject`  (
                                  id                           bigint       NOT NULL                               COMMENT 'ID',
                                  subject_name                 varchar(36)  NULL DEFAULT ''                        COMMENT '名称',
                                  subject_uid                  varchar(36)  NULL DEFAULT ''                        COMMENT 'UID',

                                  tenant_id                    varchar(20)  NULL DEFAULT '000000'                  COMMENT '租户ID',
                                  pic_url                      varchar(2048) NULL DEFAULT ''                        COMMENT '图片地址',
                                  create_time                  datetime     NULL DEFAULT NULL                      COMMENT '创建时间',
                                  create_dept                  bigint(20)   NULL DEFAULT NULL                      COMMENT '创建部门',
                                  create_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '创建人',
                                  update_time                  datetime     NULL DEFAULT NULL                      COMMENT '更新时间',
                                  update_by                    bigint(20)   NULL DEFAULT NULL                      COMMENT '更新人',
                                  del_flag                     int(1)       NULL DEFAULT 0                         COMMENT '删除标志(0：未删除，2：已删除)',

                                  PRIMARY KEY (`id`) USING BTREE,
                                  KEY `idx_subject_name` (`subject_name`) USING BTREE,
                                  KEY `idx_subject_uid` (`subject_uid`) USING BTREE

) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment ='人脸识别库';

