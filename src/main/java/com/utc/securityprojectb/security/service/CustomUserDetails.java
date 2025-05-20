package com.utc.securityprojectb.security.service;

import com.utc.securityprojectb.entity.Account;
import com.utc.securityprojectb.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
  
  private String username;
  private String password;
  private List<GrantedAuthority> authorities;
  
  public CustomUserDetails(Account account) {
    this.username = account.getUsername();
    this.password = account.getPassword();
    List<GrantedAuthority> list = new ArrayList<>();
    for (Role role : account.getRoles().stream().toList()) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName());
      list.add(authority);
    }
    this.authorities = list;
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }
  
  @Override
  public String getPassword() {
    return password;
  }
  
  @Override
  public String getUsername() {
    return username;
  }
}
