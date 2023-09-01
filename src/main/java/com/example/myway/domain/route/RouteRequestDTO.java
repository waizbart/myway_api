package com.example.myway.domain.route;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RouteRequestDTO(
        @NotBlank
        String name,

        @NotNull
        Double initial_lat,

        @NotNull
        Double initial_long,

        @NotNull
        Double final_lat,

        @NotNull
        Double final_long
) { }
