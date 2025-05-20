package com.utc.securityprojectb.configuration;


import com.utc.securityprojectb.constant.Constant;
import com.utc.securityprojectb.entity.Account;
import com.utc.securityprojectb.entity.Role;
import com.utc.securityprojectb.repository.AccountRepository;
import com.utc.securityprojectb.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfiguration {
  
  private final PasswordEncoder passwordEncoder;
  
  @NonFinal
  private static final String ADMIN_USERNAME = "admin";
  @NonFinal
  private static final String ADMIN_PASSWORD = "admin";
  
  @Bean
  @ConditionalOnProperty(
          prefix = "spring",
          value = "datasource.driver-class-name",
          havingValue = "com.mysql.cj.jdbc.Driver")
  ApplicationRunner applicationRunner(AccountRepository accountRepository, RoleRepository roleRepository) {
    return args -> {
      if (!roleRepository.existsByName(Constant.ROLE_ADMIN)) {
        roleRepository.save(Role.builder()
                                    .name(Constant.ROLE_ADMIN)
                                    .build());
        
      }
      
      if (!roleRepository.existsByName(Constant.ROLE_USER)) {
        roleRepository.save(Role.builder()
                                    .name(Constant.ROLE_USER)
                                    .build());
      }
      
      if (accountRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
        var roles = new HashSet<Role>();
        roles.add(roleRepository.findByName(Constant.ROLE_ADMIN));
        
        Account account = Account.builder()
                                  .email("null")
                                  .username(ADMIN_USERNAME)
                                  .password(passwordEncoder.encode(ADMIN_PASSWORD))
                                  .roles(roles)
                                  .build();
        
        accountRepository.save(account);
        log.warn("admin user has been created with default password: admin, please change it");
      }
      log.info("Application initialization completed .....");
    };
  }
}


