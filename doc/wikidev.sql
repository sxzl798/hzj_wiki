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
                        `desription` VARCHAR(200) COMMENT '描述',
                        `cover` VARCHAR(200) COMMENT '封面',
                        `doc_count` INT COMMENT '文档数',
                        `view_count` INT  COMMENT '阅读数',
                        `vote_count` INT COMMENT '点赞数',
                        PRIMARY KEY(`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='电子书';

INSERT INTO `ebook`(id,name,desription) VALUES(1,'Spring Boot入门教程','零基础入门Java开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id,name,desription) VALUES(2,'Vue入门教程','零基础入门Vue开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id,name,desription) VALUES(3,'Python 入门教程','零基础入门Python开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id,name,desription) VALUES(4,'Mysql入门教程','零基础入门Mysql开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id,name,desription) VALUES(5,'Oracle入门教程','零基础入门Oracle开发，企业级应用开发最佳首选框架');