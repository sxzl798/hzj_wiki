package com.hzj.wiki.job;

import com.hzj.wiki.service.DocService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DocJob {
    private static final Logger LOG = LoggerFactory.getLogger(DocJob.class);
    @Resource
    private DocService docService;
    /**
     * 固定实践间隔，fixedRate单位毫秒
     */
//    @Scheduled(fixedRate = 1000)
//    public void simple() throws InterruptedException{
//        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
//        String dataString = formatter.format(new Date());
//        Thread.sleep(2000);
//        LOG.info("每隔5秒钟执行一次：{}",dataString);
//    }

    /**
     * 自定义crom表达式跑批
     * 只有等下一次执行完成，下一次才会再下一个时间点执行，错过就错过
     */

    @Scheduled(cron = "30/5 * * * * ? ")
    public void cron() {
        docService.updateEbookInfo();
    }
}
