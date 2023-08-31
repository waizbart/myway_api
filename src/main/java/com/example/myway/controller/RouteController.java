package com.example.myway.controller;

import com.example.myway.domain.route.Route;
import com.example.myway.domain.route.RouteRequestDTO;
import com.example.myway.domain.route.RouteResponseDTO;
import com.example.myway.domain.route.RouteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("routes")
public class RouteController {

    private final RouteRepository repository;

    public RouteController(RouteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public void createRoute(@RequestBody RouteRequestDTO data){
        Route routeData = new Route(data);
        repository.save(routeData);
    }

    @GetMapping
    public List<RouteResponseDTO> getAll(){
        return repository.findAll().stream().map(RouteResponseDTO::new).toList();
    }
}
