package com.example.myway.domain.route;

public record RouteRequestDTO(
        String name,
        Double initial_lat,
        Double initial_long,
        Double final_lat,
        Double final_long
) { }
