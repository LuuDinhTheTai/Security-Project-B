package com.utc.securityprojectb.service;

import com.utc.securityprojectb.entity.Role;
import com.utc.securityprojectb.service.base.BaseService;

public interface RoleService extends BaseService<Role, String> {
  
  Role findByName(String name);
}
