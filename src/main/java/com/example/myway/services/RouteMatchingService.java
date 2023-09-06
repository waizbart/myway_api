package com.example.myway.services;

import com.example.myway.domain.route.Coordinate;
import com.example.myway.domain.route.Route;
import com.example.myway.domain.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.fastdtw.dtw.FastDTW;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.timeseries.TimeSeriesBase;
import com.fastdtw.util.DistanceFunction;
import com.fastdtw.dtw.TimeWarpInfo;

@Service
public class RouteMatchingService {
    private static final double EARTH_RADIUS = 6371000;

    public List<DistanceListDTO> matchRoute(Route myRoute, List<Route> allRoutes) throws IOException {
        Coordinate[] coordinates = myRoute.getCoordinatesJSON();

        List<DistanceListDTO> routeMatches = new ArrayList<>();

        for (Route otherRoute : allRoutes) {
            if (myRoute.getUser().getId().equals(otherRoute.getUser().getId())) {
                continue;
            }

            Coordinate[] otherCoordinates = otherRoute.getCoordinatesJSON();

            TimeSeries series1 = convertToTimeSeries(coordinates);
            TimeSeries series2 = convertToTimeSeries(otherCoordinates);

            DistanceFunction haversineDistance = (a, b) -> {
                double lat1 = a[0];
                double lon1 = a[1];
                double lat2 = b[0];
                double lon2 = b[1];

                double dLat = Math.toRadians(lat2 - lat1);
                double dLon = Math.toRadians(lon2 - lon1);

                double aVal = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2)) * Math.pow(Math.sin(dLon / 2), 2);

                double c = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));
                double distance = EARTH_RADIUS * c;

                return distance;
            };

            TimeWarpInfo warpInfo = FastDTW.compare(series1, series2, haversineDistance);

            double distance = warpInfo.getDistance();
            double percentage = (1 - (distance / EARTH_RADIUS)) * 100;

            User otherUser = otherRoute.getUser();

            DistanceListDTO match = new DistanceListDTO(otherCoordinates, otherUser.getUsername(), otherUser.getPhone(), percentage);
            routeMatches.add(match);
        }

        Collections.sort(routeMatches, Comparator.comparingDouble(DistanceListDTO::getNear_percentage));
        routeMatches = routeMatches.stream().filter(route -> route.getNear_percentage() >= 0)
                .collect(Collectors.toList());
        return routeMatches;
    }

    private static TimeSeries convertToTimeSeries(Coordinate[] coordinates) {
        TimeSeriesBase.Builder builder = new TimeSeriesBase.Builder();

        Integer i = 0;
        for (Coordinate coordinate : coordinates) {
            builder.add(i, coordinate.getLatitude(), coordinate.getLongitude());
            i += 1;
        }

        return builder.build();
    }
}
