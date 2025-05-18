package com.utc.securityprojectb.controller;

import com.utc.securityprojectb.dto.request.RegisterRequest;
import com.utc.securityprojectb.dto.response.AccountResponse;
import com.utc.securityprojectb.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts/")
public class AccountController {
  
  private final AccountService accountService;
  
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }
  
  @PostMapping("register")
  public ResponseEntity<AccountResponse> register(@Valid @RequestBody RegisterRequest request) {
    request.validate();
    return ResponseEntity
                   .status(HttpStatus.CREATED)
                   .body(accountService.register(request));
  }
}
