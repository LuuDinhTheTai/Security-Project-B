package com.utc.securityprojectb.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterRequest {
  
  private static final Pattern EMAIL_REGEX = Pattern.compile(
          "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
  );
  
  @NotEmpty
  private String email;
  @NotEmpty
  private String username;
  @NotEmpty
  private String password;
  @NotEmpty
  private String confirmPassword;
  
  public void validate() {
    isValid(this.email);
    if (!this.password.equals(this.confirmPassword)) {
      throw new IllegalArgumentException("Passwords do not match");
    }
  }
  
  private void isValid(String email) {
    if (!EMAIL_REGEX.matcher(email).matches()) {
      throw new IllegalArgumentException("Invalid email format");
    }
  }
}
