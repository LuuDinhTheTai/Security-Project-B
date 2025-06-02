package com.utc.securityprojectb.service;

import com.nimbusds.jose.JOSEException;
import com.utc.securityprojectb.dto.request.AuthenticationRequest;
import com.utc.securityprojectb.dto.request.IntrospectRequest;
import com.utc.securityprojectb.dto.request.LoginRequest;
import com.utc.securityprojectb.dto.request.LogoutRequest;
import com.utc.securityprojectb.dto.response.AuthenticationResponse;
import com.utc.securityprojectb.dto.response.IntrospectResponse;
import com.utc.securityprojectb.dto.response.LoginResponse;

import java.text.ParseException;

public interface AuthenticationService {
  
  IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
  AuthenticationResponse authenticate(AuthenticationRequest request);
  void logout(LogoutRequest request);
}
