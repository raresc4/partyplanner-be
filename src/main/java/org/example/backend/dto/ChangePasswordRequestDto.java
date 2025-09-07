package org.example.backend.dto;

import com.sun.istack.NotNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class ChangePasswordRequestDto {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String oldPassword;

    @NotNull
    @NotBlank
    private String newPassword;
}
