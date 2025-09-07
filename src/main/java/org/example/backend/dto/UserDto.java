package org.example.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserDto {
    private String id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private Date accountCreationDate;
}
