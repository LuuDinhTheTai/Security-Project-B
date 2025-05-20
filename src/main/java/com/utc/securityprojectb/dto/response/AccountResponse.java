package com.utc.securityprojectb.dto.response;

import com.utc.securityprojectb.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccountResponse {
  
  private String id;
  private String email;
  private String username;
  private String password;
  
  public static AccountResponse from(Account account) {
    return new AccountResponse(
            account.getId(),
            account.getEmail(),
            account.getUsername(),
            account.getPassword()
    );
  }
}
