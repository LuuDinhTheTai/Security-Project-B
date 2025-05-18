package com.utc.securityprojectb.repository;

import com.utc.securityprojectb.entity.Account;
import com.utc.securityprojectb.repository.base.BaseRepository;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {
  
  boolean existsByEmail(@NotEmpty String email);
  boolean existsByUsername(@NotEmpty String username);
}
