package com.utc.securityprojectb.repository;

import com.utc.securityprojectb.entity.Role;
import com.utc.securityprojectb.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
  
  Role findByName(String name);
}
