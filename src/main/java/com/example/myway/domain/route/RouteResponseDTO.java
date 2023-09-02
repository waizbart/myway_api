package com.example.myway.domain.route;

public record RouteResponseDTO(String id, String name) {
    public RouteResponseDTO(Route route){
        this(route.getId(), route.getName());
    }
}
