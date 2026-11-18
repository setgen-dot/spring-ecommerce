package com.example.config;

import com.example.security.CredentialDetailsService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CredentialDetailsService credentialDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(AbstractHttpConfigurer::disable)
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/api/v1/auth/**", "/.well-known/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .anyRequest().hasRole("ADMIN"));

        return http.build();
    }

    @Bean
    public RSAKey jwtSigningKey(ResourceLoader resources,
                                @Value("${rsa.private-key:}") String privateKeyLocation,
                                @Value("${rsa.public-key:}") String publicKeyLocation)
            throws IOException, JOSEException {
        if (privateKeyLocation.isBlank() != publicKeyLocation.isBlank()) {
            throw new IllegalArgumentException("Configure both rsa.private-key and rsa.public-key");
        }

        boolean configured = !privateKeyLocation.isBlank();
        Resource privateKeyFile = resources.getResource(configured ? privateKeyLocation : "classpath:keys/private.pem");
        Resource publicKeyFile = resources.getResource(configured ? publicKeyLocation : "classpath:keys/public.pem");
        if (!configured && !privateKeyFile.exists() && !publicKeyFile.exists()) {
            return new RSAKeyGenerator(2048)
                    .algorithm(JWSAlgorithm.RS256)
                    .keyUse(KeyUse.SIGNATURE)
                    .keyIDFromThumbprint(true)
                    .generate();
        }

        try (InputStream privatePem = privateKeyFile.getInputStream();
             InputStream publicPem = publicKeyFile.getInputStream()) {
            RSAPrivateKey privateKey = RsaKeyConverters.pkcs8().convert(privatePem);
            RSAPublicKey publicKey = RsaKeyConverters.x509().convert(publicPem);
            if (!privateKey.getModulus().equals(publicKey.getModulus())) {
                throw new IllegalArgumentException("RSA signing and verification keys must belong to the same pair");
            }
            return new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .algorithm(JWSAlgorithm.RS256)
                    .keyUse(KeyUse.SIGNATURE)
                    .keyIDFromThumbprint()
                    .build();
        }
    }

    @Bean
    public JwtEncoder jwtEncoder(RSAKey rsaKey) {
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(credentialDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
