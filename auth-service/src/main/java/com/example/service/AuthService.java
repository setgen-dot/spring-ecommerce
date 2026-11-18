package com.example.service;

import com.example.client.UserClient;
import com.example.dto.request.CreateUserRequest;
import com.example.dto.request.LoginRequest;
import com.example.dto.request.RegistrationRequest;
import com.example.exception.EmailAlreadyTakenException;
import com.example.entity.Credential;
import com.example.repository.CredentialRepository;
import com.example.security.CredentialDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final CredentialRepository credentialRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserClientService userClientService;

    @Transactional
    public String register(RegistrationRequest request) {
        if (credentialRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyTakenException(request.email());
        }

        Long userId = userClientService.createUser(new CreateUserRequest(request.firstname(), request.lastname(), request.email())).userId();

        Credential credential = Credential.builder()
                .userId(userId)
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Credential.Role.USER)
                .build();
        credentialRepository.save(credential);

        return jwtService.generateToken(credential);
    }

    public String login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication auth = authenticationManager.authenticate(authInputToken);
        CredentialDetails credentialDetails = (CredentialDetails) auth.getPrincipal();
        return jwtService.generateToken(credentialDetails.getCredential());
    }
}
