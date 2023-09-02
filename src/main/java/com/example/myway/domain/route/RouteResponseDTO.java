package com.example.myway.domain.route;

public record RouteResponseDTO(String id, String name) {
    public RouteResponseDTO(Route product){
        this(product.getId(), product.getName());
    }
}
