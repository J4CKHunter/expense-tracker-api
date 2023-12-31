package com.erdemnayin.expensetrackerapi.config;

import com.erdemnayin.expensetrackerapi.model.Role;
import com.erdemnayin.expensetrackerapi.model.Transaction;
import com.erdemnayin.expensetrackerapi.model.User;
import com.erdemnayin.expensetrackerapi.repository.TransactionRepository;
import com.erdemnayin.expensetrackerapi.repository.UserRepository;
import com.erdemnayin.expensetrackerapi.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class StartupConfig implements CommandLineRunner {


    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    private final PasswordEncoder passwordEncoder;

    public StartupConfig(UserRepository userRepository, TransactionRepository transactionRepository, PasswordEncoder passwordEncoder, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        User admin = new User(null,
                "admin",
                "admin",
                "admin@admin.com",
                passwordEncoder.encode("admin"),
                Role.ADMIN,
                null);

        User erdem = new User(null,
                "erdem",
                "nayin","erdem@nayin.com",
                passwordEncoder.encode(
                        "erdem"),
                Role.USER,
                null);

        User giorgio = new User(null,
                "giorgio",
                "moroder","giorgio@moroder.com",
                passwordEncoder.encode(
                        "giorgio"),
                Role.USER,
                null);

        User todd = new User(null,
                "todd",
                "edwards","todd@edwards.com",
                passwordEncoder.encode(
                        "todd"),
                Role.USER,
                null);

        User nile = new User(null,
                "nile",
                "rodgers","nile@rodgers.com",
                passwordEncoder.encode(
                        "nile"),
                Role.USER,
                null);

        userRepository.save(erdem);
        userRepository.save(giorgio);
        userRepository.save(admin);
        userRepository.save(todd);
        userRepository.save(nile);

        transactionRepository.save(new Transaction(
                null,
                "Porsche 956",
                LocalDate.now(),
                1000000D,
                erdem));

        transactionRepository.save(new Transaction(
                null,
                "MacBook Pro 16",
                LocalDate.now(),
                95156D,
                erdem));

        transactionRepository.save(new Transaction(
                null,
                "Transportation",
                LocalDate.now(),
                512D,
                erdem));

        transactionRepository.save(new Transaction(
                null,
                "dj set",
                LocalDate.now(),
                12D,
                giorgio));

        transactionRepository.save(new Transaction(
                null,
                "vinyl",
                LocalDate.now(),
                192D,
                giorgio));

        transactionRepository.save(new Transaction(
                null,
                "headset",
                LocalDate.now(),
                123D,
                todd));

        transactionRepository.save(new Transaction(
                null,
                "mic",
                LocalDate.now(),
                99D,
                todd));

        transactionRepository.save(new Transaction(
                null,
                "guitar",
                LocalDate.now(),
                999D,
                nile));

        transactionRepository.save(new Transaction(
                null,
                "guitar bass",
                LocalDate.now(),
                889D,
                nile));

    }
}
