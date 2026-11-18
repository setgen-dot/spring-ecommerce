package com.example.service;

import com.example.dto.request.CreateUserRequest;
import com.example.dto.response.UserProfileResponse;
import com.example.entity.User;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepository;
import com.example.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public Long create(CreateUserRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.email());
        if (optionalUser.isPresent()) return optionalUser.get().getId();

        User userToSave = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .createdAt(Instant.now())
                .build();

        return userRepository.save(userToSave).getId();
    }

    public UserProfileResponse getProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return userMapper.toProfileResponse(user);
    }
}
