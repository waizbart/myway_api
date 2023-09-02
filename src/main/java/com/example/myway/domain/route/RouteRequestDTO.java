package com.example.myway.domain.route;

import jakarta.validation.constraints.NotBlank;

public record RouteRequestDTO(
        @NotBlank
        String name
) {
}
