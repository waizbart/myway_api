package com.example.myway.domain.route;

public record RouteResponseDTO(Long id, String name,
                               Double initial_lat,
                               Double initial_long,
                               Double final_lat,
                               Double final_long) {
    public RouteResponseDTO(Route route){
        this(route.getId(), route.getName(), route.getInitial_lat(), route.getInitial_long(), route.getFinal_lat(), route.getFinal_long());
    }
}
