package com.example.myway.services;

import com.example.myway.domain.route.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistanceListDTO {
    private Coordinate[] coordinates;
    private String user_login;    
    private String user_phone;
    private double near_percentage;
}
