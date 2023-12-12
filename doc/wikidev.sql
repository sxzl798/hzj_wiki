DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS `user`(
                                     `id` INT UNSIGNED AUTO_INCREMENT KEY COMMENT '用户编号',
                                     `username` VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
                                     `password` VARCHAR(20) NOT NULL COMMENT '密码'
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT ='测试';

SELECT `id`,`username`,`password` FROM `user`;

INSERT INTO user
VALUES
    ('1','amy','amy123'),
    ('2','bob','bob123');


SELECT `id`,`username`,`password` FROM `user`;

DROP TABLE IF EXISTS `demo`;

CREATE TABLE `demo`(
                       `id` BIGINT NOT NULL COMMENT 'id',
                       `name` VARCHAR(50) COMMENT '名称',
                       PRIMARY KEY(`id`)
)	ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='测试';

DROP TABLE IF EXISTS `ebook`;

CREATE TABLE `ebook`(
                        `id` BIGINT NOT NULL COMMENT 'id',
                        `name` VARCHAR(50) COMMENT '名称',
                        `category_id` BIGINT COMMENT '分类1',
                        `category2_id` BIGINT COMMENT '分类2',
                        `description` VARCHAR(200) COMMENT '描述',
                        `cover` VARCHAR(200) COMMENT '封面',
                        `doc_count` INT COMMENT '文档数',
                        `view_count` INT  COMMENT '阅读数',
                        `vote_count` INT COMMENT '点赞数',
                        PRIMARY KEY(`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='电子书';

INSERT INTO `ebook`(id,name,description) VALUES(1,'Spring Boot入门教程','零基础入门Java开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id,name,description) VALUES(2,'Vue入门教程','零基础入门Vue开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id,name,description) VALUES(3,'Python 入门教程','零基础入门Python开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id,name,description) VALUES(4,'Mysql入门教程','零基础入门Mysql开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id,name,description) VALUES(5,'Oracle入门教程','零基础入门Oracle开发，企业级应用开发最佳首选框架');

# 分类
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`(
    `id` BIGINT NOT NULL COMMENT 'id',
    `parent` BIGINT NOT NULL DEFAULT 0 COMMENT '父id',
    `name` VARCHAR(50) NOT NULL COMMENT '名称',
    `sort` INT COMMENT '顺序',
    PRIMARY KEY (`id`)
) ENGINE = innodb DEFAULT CHARSET = utf8mb4 COMMENT '分类';

INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (100,000,'前端开发',100);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (101,100,'Vue',101);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (102,100,'HTML & CSS',102);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (200,000,'Java',200);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (201,200,'基础应用',201);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (202,200,'框架应用',202);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (300,000,'Python',300);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (301,300,'基础应用',301);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (302,300,'进阶方向应用',302);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (400,000,'数据库',400);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (401,400,'MySql',401);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (500,500,'其他',500);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (501,500,'服务器',502);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (502,500,'开发工具',502);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (503,500,'热门服务端语言',503);