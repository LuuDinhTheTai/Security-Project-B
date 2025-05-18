package com.utc.securityprojectb.service.impl;

import com.utc.securityprojectb.constant.Constant;
import com.utc.securityprojectb.dto.request.RegisterRequest;
import com.utc.securityprojectb.dto.response.AccountResponse;
import com.utc.securityprojectb.entity.Account;
import com.utc.securityprojectb.entity.Role;
import com.utc.securityprojectb.repository.AccountRepository;
import com.utc.securityprojectb.service.AccountService;
import com.utc.securityprojectb.service.RoleService;
import com.utc.securityprojectb.service.base.impl.BaseServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Long> implements AccountService {
  
  private final AccountRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;
  
  public AccountServiceImpl(AccountRepository repository, PasswordEncoder passwordEncoder, RoleService roleService) {
    super(repository);
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.roleService = roleService;
  }
  
  @Override
  public AccountResponse register(RegisterRequest request) {
    Account account = new Account();
    
    if (repository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email already exists");
    }
    if (repository.existsByUsername(request.getUsername())) {
      throw new IllegalArgumentException("Username already exists");
    }
    
    account.setEmail(request.getEmail());
    account.setUsername(request.getUsername());
    account.setPassword(passwordEncoder.encode(request.getPassword()));
    
    Set<Role> roles = Set.of(roleService.findByName(Constant.ROLE_USER));
    account.setRoles(roles);
    //
    
    return AccountResponse.from(create(account));
  }
}
