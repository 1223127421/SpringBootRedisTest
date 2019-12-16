package com.server.scheduler;

import com.model.entity.Notice;
import com.server.constants.Constant;
import com.server.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author admin
 * @Date 2019/11/26 0:09
 * @Description 定时器
 */

@Component
@EnableScheduling
public class ListListenScheduler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void putNoticeListen() {
        ListOperations<String, Notice> listOperations = redisTemplate.opsForList();
        Notice notice = listOperations.rightPop(Constant.RedisListNoticePrefix);
        while (true) {
            if (notice == null) {
                break;
            }
            //发送邮件
            emailService.putNotice(notice);

            notice = listOperations.rightPop(Constant.RedisListNoticePrefix);
        }

    }

}
