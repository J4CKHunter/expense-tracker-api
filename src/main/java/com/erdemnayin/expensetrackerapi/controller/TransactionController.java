package com.erdemnayin.expensetrackerapi.controller;

import com.erdemnayin.expensetrackerapi.dto.request.TransactionRequest;
import com.erdemnayin.expensetrackerapi.dto.response.TotalPurchaseAmountByUserResponse;
import com.erdemnayin.expensetrackerapi.dto.response.TotalPurchaseAmountResponse;
import com.erdemnayin.expensetrackerapi.dto.response.TransactionResponse;
import com.erdemnayin.expensetrackerapi.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping()
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(@RequestParam(required = false) Long userId){

        List<TransactionResponse> list = null;

        if (userId !=  null){
            list = transactionService.getAllTransactionsByUserId(userId);
        }else {
            list = transactionService.getAllTransactions();
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionByUserId(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransaction(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@RequestBody TransactionRequest transactionRequest,
                                                                 @PathVariable Long id){
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionRequest));
    }

    @GetMapping("/totalPurchaseAmount")
    public ResponseEntity<List<?>> getTotalPurchaseAmount(
            @RequestParam(required = false) Long userId){

        List<TotalPurchaseAmountByUserResponse> totalPurchaseAmountByUserList = null;
        List<TotalPurchaseAmountResponse> totalPurchaseAmountList = null;

        if (userId !=  null){
            return ResponseEntity.ok(transactionService.getTotalPurchaseAmountByUser(userId));

        }else {
            return ResponseEntity.ok(transactionService.getTotalPurchaseAmountForAll());
        }
    }




}
