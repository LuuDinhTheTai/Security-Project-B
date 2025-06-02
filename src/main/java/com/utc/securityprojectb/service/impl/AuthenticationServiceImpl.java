package com.utc.securityprojectb.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.utc.securityprojectb.dto.request.AuthenticationRequest;
import com.utc.securityprojectb.dto.request.IntrospectRequest;
import com.utc.securityprojectb.dto.request.LoginRequest;
import com.utc.securityprojectb.dto.request.LogoutRequest;
import com.utc.securityprojectb.dto.response.AuthenticationResponse;
import com.utc.securityprojectb.dto.response.IntrospectResponse;
import com.utc.securityprojectb.dto.response.LoginResponse;
import com.utc.securityprojectb.entity.InvalidatedToken;
import com.utc.securityprojectb.exception.ApiException;
import com.utc.securityprojectb.exception.ErrorCode;
import com.utc.securityprojectb.repository.AccountRepository;
import com.utc.securityprojectb.repository.InvalidatedTokenRepository;
import com.utc.securityprojectb.security.jwt.JwtUtils;
import com.utc.securityprojectb.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
  
  private final AccountRepository accountRepository;
  private final InvalidatedTokenRepository invalidatedTokenRepository;
  private final JwtUtils jwtUtils;
  
  @Override
  public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
    var token = request.getToken();
    boolean isValid = true;

    try {
      jwtUtils.verifyToken(token, false);
      
    } catch (ApiException e) {
      isValid = false;
    }

    return IntrospectResponse.builder().valid(isValid).build();
  }
  
  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    var account = accountRepository
                       .findByUsername(request.getUsername())
                       .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_EXISTS));
    
    boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());
    
    if (!authenticated) throw new ApiException(ErrorCode.UNAUTHENTICATED);
    
    var token = jwtUtils.generateToken(account);
    
    return AuthenticationResponse.builder().token(token).authenticated(true).build();
  }
  
  @Override
  public void logout(LogoutRequest request) {
    try {
      var signToken = jwtUtils.verifyToken(request.getToken(), true);

      String jit = signToken.getJWTClaimsSet().getJWTID();
      Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

      InvalidatedToken invalidatedToken =
              InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

      invalidatedTokenRepository.save(invalidatedToken);

    } catch (ApiException | JOSEException | ParseException exception) {
      log.info("Token already expired");
    } catch (Exception e) {
      log.error("Error logging out", e);
    }
  }
  
//  public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
//    var signedJWT = verifyToken(request.getToken(), true);
//
//    var jit = signedJWT.getJWTClaimsSet().getJWTID();
//    var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
//
//    InvalidatedToken invalidatedToken =
//            InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
//
//    invalidatedTokenRepository.save(invalidatedToken);
//
//    var username = signedJWT.getJWTClaimsSet().getSubject();
//
//    var user =
//            accountRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
//
//    var token = generateToken(user);
//
//    return AuthenticationResponse.builder().token(token).authenticated(true).build();
//  }
}
