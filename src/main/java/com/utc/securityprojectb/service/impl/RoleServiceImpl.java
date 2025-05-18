package com.utc.securityprojectb.service.impl;

import com.utc.securityprojectb.entity.Role;
import com.utc.securityprojectb.repository.RoleRepository;
import com.utc.securityprojectb.service.RoleService;
import com.utc.securityprojectb.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {
  
  private final RoleRepository repository;
  
  public RoleServiceImpl(RoleRepository repository) {
    super(repository);
    this.repository = repository;
  }
  
  @Override
  public Role findByName(String name) {
    return repository.findByName(name);
  }
}
