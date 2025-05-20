package com.utc.securityprojectb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column(unique = true, nullable = false)
  private String email;
  @Column(unique = true, nullable = false)
  private String username;
  @Column(nullable = false)
  private String password;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "account_role",
    joinColumns = @JoinColumn(name = "account_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;
}
