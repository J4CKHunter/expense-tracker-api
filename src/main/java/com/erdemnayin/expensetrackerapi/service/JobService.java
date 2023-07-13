package com.erdemnayin.expensetrackerapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JobService {

    Logger logger = LoggerFactory.getLogger(JobService.class);

    //everyDay -> At 01:00
    @Scheduled(fixedRate = 2000)
    public void twentySeconds() {
        System.out.println(Thread.currentThread().getName()+" Task 1 executed at "+ new Date());
    }

    //everyDay -> At 01:00
    @Scheduled(cron = "0 0 1 * * *")
    public void daily() {
        System.out.println(Thread.currentThread().getName()+" Task 1 executed at "+ new Date());
    }

    // every Monday -> At 00:00 on Monday.
    @Scheduled(cron = "0 0 0 * * MON")
    public void weekly() {
        System.out.println(Thread.currentThread().getName()+" Task 2 executed at "+ new Date());
    }

    // At 00:00 on day-of-month 1
    @Scheduled(cron = "0 0 0 1 * *")
    public void monthly() {
        System.out.println(Thread.currentThread().getName()+" Task 2 executed at "+ new Date());
    }
}
