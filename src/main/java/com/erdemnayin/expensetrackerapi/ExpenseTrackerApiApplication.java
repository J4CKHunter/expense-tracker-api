package com.erdemnayin.expensetrackerapi;

import com.erdemnayin.expensetrackerapi.config.JobRunrFactory;
import com.erdemnayin.expensetrackerapi.config.RsaKeyProperties;
import com.erdemnayin.expensetrackerapi.model.Transaction;
import com.erdemnayin.expensetrackerapi.repository.TransactionRepository;
import com.erdemnayin.expensetrackerapi.service.TransactionService;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jobrunr.scheduling.cron.Cron.every15seconds;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableTransactionManagement
public class ExpenseTrackerApiApplication {
	public static void main(String[] args){
		SpringApplication.run(ExpenseTrackerApiApplication.class, args);
	}
}


