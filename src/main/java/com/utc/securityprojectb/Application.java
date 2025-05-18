package com.utc.securityprojectb;

import com.utc.securityprojectb.constant.Constant;
import com.utc.securityprojectb.entity.Role;
import com.utc.securityprojectb.service.RoleService;
import com.utc.securityprojectb.service.impl.RoleServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
  
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
  
}
