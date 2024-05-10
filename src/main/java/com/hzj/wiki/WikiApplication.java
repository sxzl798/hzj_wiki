package com.hzj.wiki;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;


@MapperScan("com.hzj.wiki.mapper")
@SpringBootApplication
@EnableScheduling
//@EnableAsync
public class WikiApplication {
	private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(WikiApplication.class);
		Environment environment =application.run(args).getEnvironment();

		LOG.info("启动成功！！");
		LOG.info("地址:\thttp://127.0.0.1:{}",environment.getProperty("server.port"));
		SpringApplication.run(WikiApplication.class, args);


	}

}
