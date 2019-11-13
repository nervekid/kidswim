package com.kite.modules.sys.quartz.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MySchedualJob {

    @Scheduled(fixedDelay = 5000)
    public void note(){
        System.out.println("你好..");
    }

    @Scheduled(cron="0/5 * * * * ? ")
    public void noteMe(){
        System.out.println("你好...");
    }
}
