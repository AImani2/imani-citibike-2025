package imani.citibike.json;

import imani.citibike.service.CitibikeService;
import imani.citibike.service.StationUpdaterService;

import java.util.ArrayList;

public class StationLocator {
    private ArrayList<Station> stationList = new ArrayList<>();
    private StationUpdaterService stationUpdaterService;

    public StationLocator(StationUpdaterService stationUpdaterService) {
        this.stationUpdaterService = stationUpdaterService;
    }

    public void updateStationList() {
        stationUpdaterService.updateStationListWithStatus(stationList);
    }

    public Station findClosestStation(double lon, double lat, boolean isBikeSearch) {
        Station closestStation = null;
        double minDistance = Double.MAX_VALUE;

        stationUpdaterService.updateStationListWithStatus(stationList);

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
