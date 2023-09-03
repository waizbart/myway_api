package com.example.myway.domain.route;

import jakarta.validation.constraints.NotBlank;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public record RouteRequestDTO(
        @NotBlank String name,
        List<Coordinate> coordinates) {
    public String coordinatesToString() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(coordinates);
    }
}