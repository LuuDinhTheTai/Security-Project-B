package com.utc.securityprojectb.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  
  ACCOUNT_NOT_FOUND(1000, "Account not found", HttpStatus.NOT_FOUND),
  EMAIL_ALREADY_EXISTS(1001, "Email already exists", HttpStatus.BAD_REQUEST),
  USERNAME_ALREADY_EXISTS(1002, "Username already exists", HttpStatus.BAD_REQUEST),
  ;
  
  private final int code;
  private final String message;
  private final HttpStatusCode statusCode;
  
  ErrorCode(int code, String message, HttpStatusCode statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }
}