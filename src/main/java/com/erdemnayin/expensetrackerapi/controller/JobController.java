package com.erdemnayin.expensetrackerapi.controller;

import com.erdemnayin.expensetrackerapi.config.JobRunrFactory;
import com.erdemnayin.expensetrackerapi.service.TransactionService;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static org.jobrunr.scheduling.cron.Cron.every30seconds;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final TransactionService transactionService;

    public JobController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping
    public ResponseEntity<?> scheduleRecurringJob() throws InterruptedException {

        JobScheduler jobScheduler = JobRunrFactory.initializeJobRunr();

        // Schedule your job every 30 seconds
        jobScheduler.<TransactionService>scheduleRecurrently(
                every30seconds(),
                x -> x.getAllByLocalDateBetween(LocalDate.now(), LocalDate.now().plusMonths(1L))
        );

        // keep the main thread running
        Thread.currentThread().join();

        return ResponseEntity.ok("Recurring job has started.");
    }
}
