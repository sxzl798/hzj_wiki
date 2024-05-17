DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS `user`
(
    `id`       INT UNSIGNED AUTO_INCREMENT KEY COMMENT '用户编号',
    `username` VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(20) NOT NULL COMMENT '密码'
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4 COMMENT ='测试';

SELECT `id`, `username`, `password`
FROM `user`;

INSERT INTO user
VALUES ('1', 'amy', 'amy123'),
       ('2', 'bob', 'bob123');


SELECT `id`, `username`, `password`
FROM `user`;

DROP TABLE IF EXISTS `demo`;

CREATE TABLE `demo`
(
    `id`   BIGINT NOT NULL COMMENT 'id',
    `name` VARCHAR(50) COMMENT '名称',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT ='测试';

DROP TABLE IF EXISTS `ebook`;

CREATE TABLE `ebook`
(
    `id`           BIGINT NOT NULL COMMENT 'id',
    `name`         VARCHAR(50) COMMENT '名称',
    `category_id`  BIGINT COMMENT '分类1',
    `category2_id` BIGINT COMMENT '分类2',
    `description`  VARCHAR(200) COMMENT '描述',
    `cover`        VARCHAR(200) COMMENT '封面',
    `doc_count`    INT COMMENT '文档数',
    `view_count`   INT COMMENT '阅读数',
    `vote_count`   INT COMMENT '点赞数',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT ='电子书';

INSERT INTO `ebook`(id, name, description)
VALUES (1, 'Spring Boot入门教程', '零基础入门Java开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id, name, description)
VALUES (2, 'Vue入门教程', '零基础入门Vue开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id, name, description)
VALUES (3, 'Python 入门教程', '零基础入门Python开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id, name, description)
VALUES (4, 'Mysql入门教程', '零基础入门Mysql开发，企业级应用开发最佳首选框架');
INSERT INTO `ebook`(id, name, description)
VALUES (5, 'Oracle入门教程', '零基础入门Oracle开发，企业级应用开发最佳首选框架');

# 分类
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id`     BIGINT      NOT NULL COMMENT 'id',
    `parent` BIGINT      NOT NULL DEFAULT 0 COMMENT '父id',
    `name`   VARCHAR(50) NOT NULL COMMENT '名称',
    `sort`   INT COMMENT '顺序',
    PRIMARY KEY (`id`)
) ENGINE = innodb
  DEFAULT CHARSET = utf8mb4 COMMENT '分类';

INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (100, 000, '前端开发', 100);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (101, 100, 'Vue', 101);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (102, 100, 'HTML & CSS', 102);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (200, 000, 'Java', 200);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (201, 200, '基础应用', 201);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (202, 200, '框架应用', 202);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (300, 000, 'Python', 300);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (301, 300, '基础应用', 301);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (302, 300, '进阶方向应用', 302);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (400, 000, '数据库', 400);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (401, 400, 'MySql', 401);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (500, 500, '其他', 500);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (501, 500, '服务器', 502);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (502, 500, '开发工具', 502);
INSERT INTO `category` (ID, PARENT, NAME, SORT)
VALUES (503, 500, '热门服务端语言', 503);

# 文档表
DROP TABLE IF EXISTS `doc`;
CREATE TABLE `doc`
(
    `id`         BIGINT      NOT NULL COMMENT 'id',
    `ebook_id`   BIGINT      NOT NULL DEFAULT 0 COMMENT '电子书id',
    `parent`     BIGINT      NOT NULL DEFAULT 0 COMMENT '父id',
    `name`       VARCHAR(50) NOT NULL COMMENT '名称',
    `sort`       INT COMMENT '顺序',
    `view_count` INT                  DEFAULT 0 COMMENT '阅读数',
    `vote_count` INT                  DEFAULT 0 COMMENT '点赞数',
    PRIMARY KEY (`id`)
) ENGINE = innodb
  DEFAULT CHARSET = utf8mb4 COMMENT '文档';

INSERT INTO `doc` (id, ebook_id, parent, name, sort, view_count, vote_count)
VALUES (1, 1, 0, '文档1', 1, 0, 0);
INSERT INTO `doc` (id, ebook_id, parent, name, sort, view_count, vote_count)
VALUES (2, 1, 1, '文档1.1', 1, 0, 0);
INSERT INTO `doc` (id, ebook_id, parent, name, sort, view_count, vote_count)
VALUES (3, 1, 0, '文档2', 2, 0, 0);
INSERT INTO `doc` (id, ebook_id, parent, name, sort, view_count, vote_count)
VALUES (4, 1, 3, '文档2.1', 1, 0, 0);
INSERT INTO `doc` (id, ebook_id, parent, name, sort, view_count, vote_count)
VALUES (5, 1, 3, '文档2.2', 2, 0, 0);
INSERT INTO `doc` (id, ebook_id, parent, name, sort, view_count, vote_count)
VALUES (6, 1, 5, '文档2.2.1', 1, 0, 0);

DROP TABLE IF EXISTS `content`;
CREATE TABLE `content`
(
    `id`      BIGINT     NOT NULL COMMENT '文档id',
    `content` MEDIUMTEXT NOT NULL COMMENT '内容',
    PRIMARY KEY (`id`)
) ENGINE = innodb
  DEFAULT CHARSET = utf8mb4 COMMENT ='文档内容';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`         BIGINT      NOT NULL COMMENT 'ID',
    `login_name` VARCHAR(50) NOT NULL COMMENT '登陆名',
    `name`       VARCHAR(50) COMMENT '昵称',
    `password`   CHAR(32)    NOT NULL COMMENT '密码',
    PRIMARY KEY (`id`),
    UNIQUE KEY `login_name_unique` (`login_name`)
) ENGINE = innodb
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户';

INSERT INTO `user` (id, `login_name`, `name`, `password`)
VALUES (1, 'test', '测试', 'test');

SELECT COUNT(1), SUM(view_count), SUM(vote_count)
FROM doc
GROUP BY ebook_id;

# 生成快照表
DROP TABLE IF EXISTS `ebook_snapshot`;
CREATE TABLE `ebook_snapshot`
(
    `id`            BIGINT AUTO_INCREMENT NOT NULL COMMENT 'id',
    `ebook_id`      BIGINT                NOT NULL DEFAULT 0 COMMENT '电子书id',
    `date`          DATE                  NOT NULL COMMENT '快照日期',
    `view_count`    INT                   NOT NULL DEFAULT 0 COMMENT '阅读数',
    `vote_count`    INT                   NOT NULL DEFAULT 0 COMMENT '点赞数',
    `view_increase` INT                   NOT NULL DEFAULT 0 COMMENT '阅读增长',
    `vote_increase` INT                   NOT NULL DEFAULT 0 COMMENT '点赞增长',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ebook_id_date_unique` (`ebook_id`, `date`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '电子书快照表';

/**
  #方案一（ID不连续）：
  #删除今天的数据
  #为所有的电子书生成一条今天的记录
  #更新总阅读数、总点赞数
  #更新今日阅读数、今日点赞数
  #方案二（ID连续）：
  # 为所有的电子书生成一条今天的记录，如果还没有
  #更新总阅读数、总点赞数更新今日阅读数、今日点赞数
#
 */

INSERT INTO `ebook_snapshot` (ebook_id, `date`, view_count, vote_count, view_increase, vote_increase)
SELECT t1.id, CURDATE(), 0, 0, 0, 0
FROM ebook t1
WHERE NOT EXISTS(SELECT 1
                 FROM ebook_snapshot t2
                 WHERE t1.id = t2.ebook_id
                   AND t2.`date` = CURDATE());

UPDATE ebook_snapshot t1, ebook t2
SET t1.view_count = t2.view_count,
    t1.vote_count = t2.vote_count
WHERE t1.`date` = CURDATE()
  AND t1.ebook_id = t2.id;

# 获取昨天数据
SELECT t1.ebook_id, view_count, vote_count
FROM ebook_snapshot t1
WHERE t1.`date` = date_sub(CURDATE(), INTERVAL 1 DAY);

UPDATE ebook_snapshot t1 LEFT JOIN (SELECT ebook_id, view_count, vote_count
                          FROM ebook_snapshot
                          WHERE `date` = date_sub(CURDATE(), INTERVAL 1 DAY)) t2
ON t1.ebook_id = t2.ebook_id
SET t1.view_increase = (t1.view_count - ifnull(t2.view_count,0)),
    t1.vote_increase = (t1.vote_count - ifnull(t2.vote_count,0))
WHERE t1.`date` = CURDATE();

SELECT
    t1.`date` as `date`,
    sum(t1.view_count) as viewCount,
    sum(t1.vote_count) as voteCount,
    sum(t1.view_increase) as viewIncrease,
    sum(t1.vote_increase) as voteIncrease
FROM ebook_snapshot t1
WHERE t1.`date` >= DATE_SUB(CURDATE(), INTERVAL 1 DAY)
GROUP BY t1.`date`
ORDER BY t1.`date` ASC;
