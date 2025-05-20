package com.utc.securityprojectb.repository;

import com.utc.securityprojectb.entity.Account;
import com.utc.securityprojectb.repository.base.BaseRepository;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account, String> {
  
  boolean existsByEmail(@NotEmpty String email);
  boolean existsByUsername(@NotEmpty String username);
  Optional<Account> findByUsername(String username);
}
