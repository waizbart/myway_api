package com.example.myway.services;

import com.example.myway.domain.route.Coordinate;
import com.example.myway.domain.route.Route;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.fastdtw.dtw.FastDTW;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.timeseries.TimeSeriesBase;
import com.fastdtw.util.DistanceFunction;
import com.fastdtw.dtw.TimeWarpInfo;
import com.fastdtw.dtw.WarpPath;

@Service
public class RouteMatchingService {
    // Raio médio da Terra em metros
    private static final double EARTH_RADIUS = 6371000; // Em metros

    public void matchRoute(Route myRoute, List<Route> allRoutes) throws IOException {
        Coordinate[] coordinates = myRoute.getCoordinatesJSON();

        for (Route otherRoute : allRoutes) {
            if (myRoute.getId().equals(otherRoute.getId())) {
                continue;
            }

            Coordinate[] otherCoordinates = otherRoute.getCoordinatesJSON();

            TimeSeries series1 = convertToTimeSeries(coordinates);
            TimeSeries series2 = convertToTimeSeries(otherCoordinates);

            // Definir uma métrica de distância personalizada com Haversine
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

            // Calcular a distância DTW entre as séries temporais usando "fastdtw" com
            // Haversine
            TimeWarpInfo warpInfo = FastDTW.compare(series1, series2, haversineDistance);

            // Imprimir a distância DTW
            System.out.println("Distância DTW entre as rotas " + myRoute.getName() + " e " + otherRoute.getName() + " = " + warpInfo.getDistance() + " metros");
        }
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
