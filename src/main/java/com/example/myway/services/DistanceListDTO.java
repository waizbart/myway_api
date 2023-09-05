package com.example.myway.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistanceListDTO {
    private String route_id;    
    private String user_id;    
    private double near_percentage;
}
