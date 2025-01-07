package imani.citibike.json;

import imani.citibike.service.CitibikeService;
import imani.citibike.service.CitibikeServiceFactory;
import imani.citibike.service.StationUpdaterService;

import java.util.ArrayList;

public class StationLocator {
    private ArrayList<Station> stationList = new ArrayList<>();

    private CitibikeService citibikeService = new CitibikeServiceFactory().getService();;
    private StationUpdaterService stationUpdaterService;

    public StationLocator(StationUpdaterService stationUpdaterService, CitibikeService citibikeService) {
        if (stationUpdaterService == null || citibikeService == null) {
            throw new IllegalArgumentException("Dependencies must not be null");
        }
        this.stationUpdaterService = stationUpdaterService;
        this.citibikeService = citibikeService;

    }

    public Station findClosestStation(double lon, double lat, boolean isBikeSearch, StationUpdaterService sus) {
        Station closestStation = null;
        double minDistance = Double.MAX_VALUE;

        sus.updateStationListWithStatus(stationList);

        for (Station station : stationList) {
            double currDistance = Math.sqrt((lat - station.lat) * (lat - station.lat))
                    + ((lon - station.lon) * (lon - station.lon));
            if (isBikeSearch && currDistance < minDistance && station.num_bikes_available > 0) {
                minDistance = currDistance;
                closestStation = station;
            } else if (!isBikeSearch && currDistance < minDistance && station.num_docks_available > 0) {
                minDistance = currDistance;
                closestStation = station;
            }

        }
        return closestStation;
    }
}
