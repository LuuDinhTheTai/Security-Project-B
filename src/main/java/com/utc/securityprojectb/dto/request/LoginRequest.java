package com.utc.securityprojectb.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
  
  @NotEmpty
  private String username;
  @NotEmpty
  private String password;
  
  public void validate() {
  
  }
}
