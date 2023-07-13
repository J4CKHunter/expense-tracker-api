package com.erdemnayin.expensetrackerapi.repository;

import com.erdemnayin.expensetrackerapi.model.Transaction;
import com.erdemnayin.expensetrackerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> getAllByLocalDateBetween(LocalDate startDate, LocalDate endDate);
    List<Transaction> getAllByLocalDate(LocalDate today);
}
