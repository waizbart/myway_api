package com.example.myway.repositories;

import com.example.myway.domain.route.Route;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Route, String> {
}
