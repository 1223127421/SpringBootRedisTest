package com.server.scheduler;

import com.model.entity.Notice;
import com.server.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description
 */
@Component
@EnableScheduling
public class NoticeListenerScheduler {

    @Autowired
    private RedisTemplate redisTemplate;

//    @Scheduled(cron = "0/10 * * * * ?")
    public void schedulerNotice() {
        String key = Constant.RedisListNoticePrefix;
        ListOperations<String, Notice> listOperations = redisTemplate.opsForList();
        Notice notice = listOperations.rightPop(key);
        while (true) {
            if (notice != null) {
                System.out.println("发送通知：" + notice.getContent());
            }
            notice = listOperations.rightPop(key);
            if (notice == null) {
                return;
            }
        }
    }

}
