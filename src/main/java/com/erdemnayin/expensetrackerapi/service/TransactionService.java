package com.erdemnayin.expensetrackerapi.service;

import com.erdemnayin.expensetrackerapi.dto.request.TransactionRequest;
import com.erdemnayin.expensetrackerapi.dto.response.TotalPurchaseAmountByUserResponse;
import com.erdemnayin.expensetrackerapi.dto.response.TotalPurchaseAmountResponse;
import com.erdemnayin.expensetrackerapi.dto.response.TransactionResponse;
import com.erdemnayin.expensetrackerapi.exception.GenericException;
import com.erdemnayin.expensetrackerapi.model.Transaction;
import com.erdemnayin.expensetrackerapi.model.User;
import com.erdemnayin.expensetrackerapi.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final UserService userService;

    public TransactionService( TransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }


    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(TransactionService::convertResponse)
                .toList();
    }

    public TransactionResponse getTransactionById(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new GenericException("Transaction not found by given id.", HttpStatus.NOT_FOUND));

        return convertResponse(transaction);
    }

    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        final Transaction transaction = new Transaction();
        transaction.setDetail(transactionRequest.getDetail());
        transaction.setPurchaseAmount(transactionRequest.getPurchaseAmount());
        transaction.setDateTime(LocalDateTime.now());
        transaction.setUser(userService.getUserById(transactionRequest.getUserId()));

        Transaction savedTransaction = transactionRepository.save(transaction);

        return convertResponse(savedTransaction);

//        return TransactionResponse.builder()
//                .id(savedTransaction.getId())
//                .detail(savedTransaction.getDetail())
//                .dateTime(savedTransaction.getDateTime())
//                .purchaseAmount(savedTransaction.getPurchaseAmount())
//                .userId(savedTransaction.getUser().getId())
//                .build();
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public TransactionResponse updateTransaction(Long id, TransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new GenericException("Transaction not found by given id.", HttpStatus.NOT_FOUND));

        transaction.setDetail(transactionRequest.getDetail());
        transaction.setPurchaseAmount(transactionRequest.getPurchaseAmount());
        transaction.setUser(userService.getUserById(transactionRequest.getUserId()));

        Transaction savedTransaction = transactionRepository.save(transaction);

        return convertResponse(savedTransaction);

//        return TransactionResponse.builder()
//                .id(savedTransaction.getId())
//                .detail(savedTransaction.getDetail())
//                .dateTime(savedTransaction.getDateTime())
//                .purchaseAmount(savedTransaction.getPurchaseAmount())
//                .userId(savedTransaction.getUser().getId())
//                .build();
    }



    public List<TransactionResponse> getAllTransactionsByUserId(Long id) {

        User user = userService.getUserById(id);

        return user.getTransactions().stream()
                .map(TransactionService::convertResponse)
                .toList();
    }

    private static TransactionResponse convertResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .detail(transaction.getDetail())
                .dateTime(transaction.getDateTime())
                .purchaseAmount(transaction.getPurchaseAmount())
                .userId(transaction.getUser().getId())
                .build();
    }

    // this is for the transactional scenario
    public static Transaction convertResponse(TransactionRequest transactionRequest) {

        return new Transaction(
                null,
                transactionRequest.getDetail(),
                LocalDateTime.now(),
                transactionRequest.getPurchaseAmount(),
                null);
    }

    // new endpoint

    public List<TotalPurchaseAmountResponse> getTotalPurchaseAmountForAll() {

        List<TotalPurchaseAmountResponse> list = new ArrayList<>();
        List<User> users = userService.getAllUsers();
        Double totalPurchaseAmount = null;

        for (User user : users) {
            totalPurchaseAmount = user.getTransactions().stream()
                    .map(Transaction::getPurchaseAmount)
                    .reduce(0D, Double::sum);

            list.add(TotalPurchaseAmountResponse.builder()
                    .totalPurchaseAmount(totalPurchaseAmount)
                    .userId(user.getId())
                    .build());

        }

        return list;


    }

    public List<TotalPurchaseAmountByUserResponse> getTotalPurchaseAmountByUser(Long id) {
        User user = userService.getUserById(id);

        Double totalPurchaseAmount = user.getTransactions().stream()
                .map(Transaction::getPurchaseAmount)
                .reduce(0D, Double::sum);

        List<TransactionResponse> transactions = user.getTransactions().stream()
                .map(TransactionService::convertResponse)
                .toList();

        return List.of(
                TotalPurchaseAmountByUserResponse.builder()
                        .totalPurchaseAmount(totalPurchaseAmount)
                        .transactions(transactions)
                        .userId(user.getId())
                        .build()
        );
    }




}
