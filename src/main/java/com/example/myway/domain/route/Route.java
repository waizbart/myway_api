package com.example.myway.domain.route;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "routes")
@Entity(name = "routes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Route {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double initial_lat;
    private Double initial_long;
    private Double final_lat;
    private Double final_long;

    public Route(RouteRequestDTO data){
        this.name= data.name();
        this.initial_lat = data.initial_lat();
        this.initial_long = data.initial_long();
        this.final_lat = data.final_lat();
        this.final_long = data.final_long();
    }
}