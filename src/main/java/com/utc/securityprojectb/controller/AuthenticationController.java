package com.utc.securityprojectb.controller;

import com.utc.securityprojectb.dto.request.AuthenticationRequest;
import com.utc.securityprojectb.dto.request.LoginRequest;
import com.utc.securityprojectb.dto.ApiResponse;
import com.utc.securityprojectb.dto.response.AuthenticationResponse;
import com.utc.securityprojectb.dto.response.LoginResponse;
import com.utc.securityprojectb.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
  
  private final AuthenticationService authenticationService;
  
  @PostMapping("login")
  public ApiResponse<?> login(@Valid @RequestBody AuthenticationRequest request) {
//    request.validate();
    return ApiResponse.<AuthenticationResponse>builder()
                   .result(authenticationService.authenticate(request))
                   .build();
  }
  
  @RequestMapping("logout")
  public void logout() {}
}
