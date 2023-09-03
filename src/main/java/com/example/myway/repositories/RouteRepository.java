package com.example.myway.repositories;

import com.example.myway.domain.route.Route;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, String> {
    List<Route> findByUser_Id(String userId);
    Optional<Route> findById(String id);
}
