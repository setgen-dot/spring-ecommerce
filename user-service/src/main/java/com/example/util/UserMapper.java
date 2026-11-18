package com.example.util;

import com.example.dto.response.UserProfileResponse;
import com.example.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfileResponse toProfileResponse(User user);
}
