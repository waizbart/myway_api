package com.example.myway.controllers;

import com.example.myway.domain.route.Route;
import com.example.myway.domain.route.RouteRequestDTO;
import com.example.myway.domain.route.RouteResponseDTO;
import com.example.myway.repositories.RouteRepository;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("route")
public class RouteController {

    @Autowired
    RouteRepository repository;

    @PostMapping
    public ResponseEntity<Route> postRoute(@RequestBody @Valid RouteRequestDTO body){
        this.repository.save(new Route(body));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<RouteResponseDTO>> getAllRoutes(){
        return ResponseEntity.ok(this.repository.findAll().stream().map(RouteResponseDTO::new).toList());
    }
}
