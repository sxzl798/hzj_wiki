//package com.hzj.wiki.job;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Component
//public class TestJob {
//    private static final Logger LOG = LoggerFactory.getLogger(TestJob.class);
//    /**
//     * 固定实践间隔，fixedRate单位毫秒
//     */
//    @Scheduled(fixedRate = 1000)
//    public void simple() throws InterruptedException{
//        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
//        String dataString = formatter.format(new Date());
//        Thread.sleep(2000);
//        LOG.info("每隔5秒钟执行一次：{}",dataString);
//    }
//
//    /**
//     * 自定义crom表达式跑批
//     * 只有等下一次执行完成，下一次才会再下一个时间点执行，错过就错过
//     */
//
//    @Scheduled(cron = "*/1 * * * * ?")
//    public void cron() throws InterruptedException {
//        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
//        String dateString = formatter.format(new Date());
//        Thread.sleep(1500);
//        LOG.info("每隔1.5秒钟执行一次：{}",dateString);
//    }
//}
