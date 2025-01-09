package imani.citibike.json;

import imani.citibike.service.CitibikeService;
import imani.citibike.service.StationUpdaterService;

import java.util.ArrayList;

public class StationLocator {
    private ArrayList<Station> stationList = new ArrayList<>();

    public StationLocator() {
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
