package com.utc.securityprojectb.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.utc.securityprojectb.constant.Constant;
import com.utc.securityprojectb.entity.Account;
import com.utc.securityprojectb.exception.ApiException;
import com.utc.securityprojectb.exception.ErrorCode;
import com.utc.securityprojectb.repository.InvalidatedTokenRepository;
import com.utc.securityprojectb.security.service.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.AcceptEncoding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {
  
  @NonFinal
  @Value("${jwt.signerKey}")
  protected String SIGNER_KEY;
  @NonFinal
  @Value("${jwt.valid-duration}")
  protected long VALID_DURATION;
  @NonFinal
  @Value("${jwt.refreshable-duration}")
  protected long REFRESHABLE_DURATION;
  
  private final InvalidatedTokenRepository invalidatedTokenRepository;
  
  public String generateToken(Account account) {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
    
    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                                        .subject(account.getUsername())
                                        .issuer(Constant.issuer)
                                        .issueTime(new Date())
                                        .expirationTime(new Date(
                                                Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                                        .jwtID(UUID.randomUUID().toString())
                                        .build();
    
    Payload payload = new Payload(jwtClaimsSet.toJSONObject());
    
    JWSObject jwsObject = new JWSObject(header, payload);
    
    try {
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();
      
    } catch (JOSEException e) {
      log.error("Cannot create token", e);
      throw new RuntimeException(e);
    }
  }
  
  public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
    JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
    
    SignedJWT signedJWT = SignedJWT.parse(token);
    
    Date expiryTime = (isRefresh)
                              ? new Date(signedJWT
                                                 .getJWTClaimsSet()
                                                 .getIssueTime()
                                                 .toInstant()
                                                 .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                                                 .toEpochMilli())
                              : signedJWT.getJWTClaimsSet().getExpirationTime();
    
    var verified = signedJWT.verify(verifier);
    
    if (!(verified && expiryTime.after(new Date()))) throw new ApiException(ErrorCode.UNAUTHENTICATED);
    
    if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    
    return signedJWT;
  }
  
  public String generateJwtToken(Authentication authentication) {
    
    CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
    
    return Jwts.builder()
                   .setSubject((userPrincipal.getUsername()))
                   .setIssuedAt(new Date())
                   .setExpiration(new Date((new Date()).getTime() + VALID_DURATION))
                   .signWith(key(), SignatureAlgorithm.HS256)
                   .compact();
  }
  
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SIGNER_KEY));
  }
  
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
                   .parseClaimsJws(token).getBody().getSubject();
  }
  
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
      
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      throw new ApiException(ErrorCode.INVALID_TOKEN);
      
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
      throw new ApiException(ErrorCode.EXPIRED_TOKEN);
      
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
      throw new ApiException(ErrorCode.UNSUPPORTED_TOKEN);
      
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
      throw new ApiException(ErrorCode.INVALID_TOKEN);
    }
  }
}
