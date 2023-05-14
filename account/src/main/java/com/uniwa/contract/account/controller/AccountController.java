package com.uniwa.contract.account.controller;

import com.uniwa.contract.account.exception.InvalidAccountCredentials;
import com.uniwa.contract.account.model.AccountDetails;
import com.uniwa.contract.account.model.AuthRequest;
import com.uniwa.contract.account.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/v1/auth", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {

        return ResponseEntity.ok(accountService.authenticate(request.getUsername(), request.getPassword()));
    }


    @GetMapping(value = "/v1/get/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public ResponseEntity<AccountDetails> accountDetails(@PathVariable String username) {

        return ResponseEntity.ok(accountService.getAccountDetails(username));

    }

    @ExceptionHandler({ InvalidAccountCredentials.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception e) {

        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }


}
