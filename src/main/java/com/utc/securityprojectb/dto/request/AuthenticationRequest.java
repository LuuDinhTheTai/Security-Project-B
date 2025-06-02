package com.utc.securityprojectb.dto.request;

import com.utc.securityprojectb.exception.ApiException;
import com.utc.securityprojectb.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
  
  private String username;
  private String password;
  
  public void validate() {
    if (username == null || username.isEmpty()) {
      throw new ApiException(ErrorCode.USERNAME_INVALID);
    }
    if (password == null || password.isEmpty()) {
      throw new ApiException(ErrorCode.PASSWORD_INVALID);
    }
  }
}
