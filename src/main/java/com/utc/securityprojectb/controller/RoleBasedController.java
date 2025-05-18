package com.utc.securityprojectb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role-based/")
public class RoleBasedController {
  
  @GetMapping("admin")
  public ResponseEntity<?> admin() {
    return ResponseEntity.ok("Admin's role");
  }
  
  @GetMapping("user")
  public ResponseEntity<?> user() {
    return ResponseEntity.ok("User's role");
  }
}
