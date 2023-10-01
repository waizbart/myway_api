package com.example.myway.domain.route;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

import com.example.myway.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Table(name = "route")
@Entity(name = "route")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;   

    @Column(columnDefinition ="LONGTEXT")
    private String coordinates;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Route(RouteRequestDTO data, User user) throws IOException  {
        this.name = data.name();
        this.coordinates = data.coordinatesToString();
        this.user = user;
    }

    public Coordinate[] getCoordinatesJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(this.coordinates, Coordinate[].class);
    }
}