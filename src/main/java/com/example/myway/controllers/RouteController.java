package com.example.myway.controllers;

import com.example.myway.domain.route.Route;
import com.example.myway.domain.user.User;
import com.example.myway.domain.route.RouteRequestDTO;
import com.example.myway.domain.route.RouteResponseDTO;
import com.example.myway.repositories.RouteRepository;
import com.example.myway.services.DistanceListDTO;
import com.example.myway.services.RouteMatchingService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController()
@RequestMapping("route")
public class RouteController {

    @Autowired
    RouteRepository repository;

    @PostMapping
    public ResponseEntity<Route> postRoute(@RequestBody @Valid RouteRequestDTO body) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            User authenticatedUser = (User) authentication.getPrincipal();

            Route route = new Route(body, authenticatedUser);
            this.repository.save(route);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RouteResponseDTO>> getAllRoutes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String userId = ((User) authentication.getPrincipal()).getId();
            List<Route> userRoutes = repository.findByUser_Id(userId);

            List<RouteResponseDTO> response = userRoutes.stream()
                    .map(route -> new RouteResponseDTO(route))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<List<DistanceListDTO>> getRoute(@PathVariable String id) throws IOException {
        Route route = repository.findById(id).orElse(null);
        List<Route> routes = repository.findAll();

        if (route != null) {
            RouteMatchingService matchingService = new RouteMatchingService();
            List<DistanceListDTO> routesMatch = matchingService.matchRoute(route, routes);

            return ResponseEntity.ok(routesMatch);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
