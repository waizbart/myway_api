package com.example.myway.repositories;

import com.example.myway.domain.route.Route;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, String> {
}
