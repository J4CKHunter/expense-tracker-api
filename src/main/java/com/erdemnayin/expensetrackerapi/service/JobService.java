package com.erdemnayin.expensetrackerapi.service;

import com.erdemnayin.expensetrackerapi.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobService {

    private final TransactionService transactionService;

    Logger logger = LoggerFactory.getLogger(JobService.class);

    public JobService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //everyDay -> At 01:00
    @Scheduled(fixedRate = 10000)
    public void twentySeconds() {
        List<Transaction> transactionList = transactionService.getAllByLocalDate(LocalDate.now());

        Double totalPurchaseAmount = transactionList.stream()
                .map(Transaction::getPurchaseAmount)
                .reduce(0D, Double::sum);

        // calculate all
        logger.info("DAILY: " + LocalDate.now() + " - TOTAL PURCHASE FOR ALL -> " + totalPurchaseAmount);
    }

    //everyDay -> At 01:00
    @Scheduled(cron = "0 0 1 * * *")
    public void daily() {
        List<Transaction> transactionList = transactionService.getAllByLocalDate(LocalDate.now());

        Double totalPurchaseAmount = transactionList.stream()
                .map(Transaction::getPurchaseAmount)
                .reduce(0D, Double::sum);

        // calculate all
        logger.info("DAILY: " + LocalDate.now() + " - TOTAL PURCHASE FOR ALL -> " + totalPurchaseAmount);
    }

    // every Monday -> At 00:00 on Monday.
    @Scheduled(cron = "0 0 0 * * MON")
    public void weekly() {
        List<Transaction> transactionList = transactionService.getAllByLocalDateBetween(LocalDate.now(),LocalDate.now().plusWeeks(1L));

        Double totalPurchaseAmount = transactionList.stream()
                .map(Transaction::getPurchaseAmount)
                .reduce(0D, Double::sum);

        // calculate all
        logger.info("WEEKLY: " + LocalDate.now() + " - TOTAL PURCHASE FOR ALL -> " + totalPurchaseAmount);
    }

    // At 00:00 on day-of-month 1
    @Scheduled(cron = "0 0 0 1 * *")
    public void monthly() {
        List<Transaction> transactionList = transactionService.getAllByLocalDateBetween(LocalDate.now(),LocalDate.now().plusWeeks(1L));

        Double totalPurchaseAmount = transactionList.stream()
                .map(Transaction::getPurchaseAmount)
                .reduce(0D, Double::sum);

        // calculate all
        logger.info("MONTHLY: " + LocalDate.now() + " - TOTAL PURCHASE FOR ALL -> " + totalPurchaseAmount);
    }
}
