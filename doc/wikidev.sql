DROP TABLE IF EXISTS `demo`;

CREATE TABLE `demo`(
                       `id` BIGINT NOT NULL COMMENT 'id',
                       `name` VARCHAR(50) COMMENT '名称',
                       PRIMARY KEY(`id`)
)	ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='测试';