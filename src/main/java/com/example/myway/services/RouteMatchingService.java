package com.example.myway.services;

import com.example.myway.domain.route.Coordinate;
import com.example.myway.domain.route.Route;
import com.example.myway.repositories.RouteRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteMatchingService {

    @Autowired
    RouteRepository repository;
    
    public void matchRoute(String routeId) {
        Route route = repository.findById(routeId).get();

        String coordinates = route.getCoordinates();
        List<Route> allRoutes = repository.findAll();

        for (Route otherRoute : allRoutes) {
            String otherCoordinates = otherRoute.getCoordinates();

            double percentage = levenshteinDistancePercent(coordinates, otherCoordinates);

            if (percentage > 90.0) {
                System.out.println("Match found!");
                System.out.println("Route 1: " + route.getName());
                System.out.println("Route 2: " + otherRoute.getName());
                System.out.println("Percentage: " + percentage);
            }
        }
    }

    public static double levenshteinDistancePercent(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();

        int[][] matrix = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            matrix[0][j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;
                matrix[i][j] = Math.min(
                    Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1),
                    matrix[i - 1][j - 1] + cost
                );
            }
        }

        int maxLength = Math.max(str1.length(), str2.length());
        double percentage = (1.0 - (double) matrix[len1][len2] / maxLength) * 100.0;
        return percentage;
    }
}
