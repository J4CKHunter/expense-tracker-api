package com.erdemnayin.expensetrackerapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detail;

    private LocalDateTime localDateTime;

    private Double purchaseAmount;

    /*TODO: THINK CASCADE TYPE*/
    @ManyToOne
    private User user;
}
