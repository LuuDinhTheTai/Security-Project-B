package com.utc.securityprojectb.configuration;

import com.utc.securityprojectb.configuration.security.CustomUserDetailsService;
import com.utc.securityprojectb.constant.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                                                                    .requestMatchers(HttpMethod.GET, "/accounts/{id}").permitAll()
                                                                    .requestMatchers(HttpMethod.POST, "/accounts/register").permitAll()
                                                                    
                                                                    .requestMatchers(HttpMethod.GET, "/role-based/admin").hasRole(Constant.ROLE_ADMIN)
                                                                    
                                                                    .requestMatchers(HttpMethod.GET, "/role-based/user").hasRole(Constant.ROLE_USER)
                                                                    
                                                                    .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable);
    
    return httpSecurity.build();
  }
  
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    
    return authenticationProvider;
  }
  
  @Bean
  public UserDetailsService userDetailsService() {
    return new CustomUserDetailsService();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
