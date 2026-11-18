package com.example.service;

import com.example.entity.Credential;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public String generateToken(Credential credential) {
        Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofDays(7));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(credential.getUserId().toString())
                .claim("role", credential.getRole())
                .issuer("http://localhost:8081")
                .issuedAt(now)
                .expiresAt(expiration)
                .build();

        JwsHeader header = JwsHeader.with(SignatureAlgorithm.RS256).build();

        return  jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
