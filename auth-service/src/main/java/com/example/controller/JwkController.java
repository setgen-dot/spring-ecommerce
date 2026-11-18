package com.example.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwkController {

    private final RSAKey signingKey;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getJwks() {
        return new JWKSet(signingKey.toPublicJWK()).toJSONObject();
    }
}
