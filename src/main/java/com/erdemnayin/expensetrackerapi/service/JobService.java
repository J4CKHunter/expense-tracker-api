package com.erdemnayin.expensetrackerapi.service;

import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.spring.annotations.Recurring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

//@Service
//public class JobService {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Recurring(id = "my-recurring-job", cron = "*/2 * * * *")
//    @Job(name = "A recurring job")
//    public void recurringJob() throws InterruptedException {
//        logger.info("The recurring job has begun.");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            logger.error("Error while executing recurring job", e);
//            throw e;
//        } finally {
//            logger.info("Recurring job has finished...");
//        }
//    }
//}
