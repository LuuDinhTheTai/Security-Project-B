package com.utc.securityprojectb.exception;

import com.utc.securityprojectb.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(value = ApiException.class)
    ResponseEntity<?> handlingAppException(ApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
    
}
