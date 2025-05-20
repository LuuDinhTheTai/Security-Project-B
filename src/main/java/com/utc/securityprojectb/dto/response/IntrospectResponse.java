package com.utc.securityprojectb.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class IntrospectResponse {
  
  private boolean valid;
}
