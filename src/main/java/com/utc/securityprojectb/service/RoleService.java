package com.utc.securityprojectb.service;

import com.utc.securityprojectb.entity.Role;
import com.utc.securityprojectb.service.base.BaseService;

public interface RoleService extends BaseService<Role, Long> {
  
  Role findByName(String name);
}
