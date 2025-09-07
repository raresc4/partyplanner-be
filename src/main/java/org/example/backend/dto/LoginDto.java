package org.example.backend.dto;

import com.sun.istack.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    @NotBlank
    @NotNull
    private String username;

    @NotNull
    @NotBlank
    private String password;
}
