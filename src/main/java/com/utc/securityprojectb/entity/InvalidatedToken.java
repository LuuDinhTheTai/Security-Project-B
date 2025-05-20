package com.utc.securityprojectb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InvalidatedToken {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private Date expiryTime;
}

