package com.erdemnayin.expensetrackerapi.service;

import com.erdemnayin.expensetrackerapi.exception.GenericException;
import com.erdemnayin.expensetrackerapi.repository.UserRepository;
import com.erdemnayin.expensetrackerapi.model.SecurityUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class JpaUserDetailsService implements UserDetailsService {
    // to be used for http.userDetailsService in securityConfig

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(() -> new GenericException("User not found with the email: " + email,
                        HttpStatus.NOT_FOUND));
    }
}
