package com.utc.securityprojectb.controller;

import com.utc.securityprojectb.dto.request.RegisterRequest;
import com.utc.securityprojectb.dto.response.AccountResponse;
import com.utc.securityprojectb.dto.response.ApiResponse;
import com.utc.securityprojectb.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/")
public class AccountController {
  
  private final AccountService accountService;
  
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }
  
  @PostMapping("register")
  public ApiResponse<?> register(@Valid @RequestBody RegisterRequest request) {
    request.validate();
    return ApiResponse.<AccountResponse>builder()
                   .code(HttpStatus.CREATED.value())
                   .result(accountService.register(request))
                   .build();
  }
  
  @GetMapping("{id}")
  public ApiResponse<?> profile(@PathVariable("id") Long id) {
    return ApiResponse.<AccountResponse>builder()
                   .code(HttpStatus.OK.value())
                   .result(accountService.findById(id))
                   .build();
  }
}
