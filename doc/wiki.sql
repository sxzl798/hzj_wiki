DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS `user`(
                                     `id` INT UNSIGNED AUTO_INCREMENT KEY COMMENT '用户编号',
                                     `username` VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
                                     `password` VARCHAR(20) NOT NULL COMMENT '密码'
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT ='测试';

SELECT `id`,`username`,`password` FROM `user`;

select `id`,`username`,`password` from `user`;