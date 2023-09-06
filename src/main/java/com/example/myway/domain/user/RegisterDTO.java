package com.example.myway.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
                @NotNull @NotBlank String login,
                @NotNull @NotBlank String password,                
                @NotNull @NotBlank String phone,
                UserRole role,
                @NotNull Boolean hasCar) {
        public RegisterDTO {
                role = UserRole.USER;
                if (hasCar == null) hasCar = false;
        }
}
