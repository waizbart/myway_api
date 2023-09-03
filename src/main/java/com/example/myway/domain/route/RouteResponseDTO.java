package com.example.myway.domain.route;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public record RouteResponseDTO(String id, String name, List<Coordinate> coordinates) {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public RouteResponseDTO(Route route) {
        this(route.getId(), route.getName(), deserializeCoordinates(route.getCoordinates()));
    }

    private static List<Coordinate> deserializeCoordinates(String coordinatesJson) {
        try {
            return objectMapper.readValue(coordinatesJson, new TypeReference<List<Coordinate>>() {});
        } catch (IOException e) {
            e.printStackTrace(); 
            return null;
        }
    }
}
