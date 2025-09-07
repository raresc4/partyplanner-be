package org.example.backend.mapper;

import lombok.AllArgsConstructor;
import org.example.backend.dto.UserDto;
import org.example.backend.models.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
    public User toModel(UserDto userDto) {
        return User.builder()
                .id(userDto.getId() != null ? userDto.getId() : null)
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .accountCreationDate(userDto.getAccountCreationDate() != null ? userDto.getAccountCreationDate() : null)
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .accountCreationDate(user.getAccountCreationDate())
                .build();
    }
}
