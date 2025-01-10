package imani.citibike.service;

import imani.citibike.json.Station;
import imani.citibike.json.StationStatus;
import imani.citibike.json.Stations;
import imani.citibike.json.StationsCache;

import java.util.ArrayList;
import java.util.List;

public class StationUpdaterService {
    private final CitibikeService citibikeService;
    private final StationsCache stationsCache;
    private Stations stations;

    public StationUpdaterService(CitibikeService citibikeService, StationsCache stationsCache) {
        this.citibikeService = citibikeService;
        this.stationsCache = stationsCache;
    }

    public ArrayList<Station> updateStationListWithStatus() {
        ArrayList<Station> stationList = new ArrayList<>();

        try {
            stations = stationsCache.getStations();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        var stationStatusResponse = citibikeService.getStationStatusResponse()
                .blockingGet();

        List<StationStatus> statuses = stationStatusResponse.data.stations;

        for (Station station : stations.data.stations) {
            statuses.stream()
                    .filter(status -> status.station_id.equals(station.station_id))
                    .findFirst()
                    .ifPresent(status -> {
                        station.num_bikes_available = status.num_bikes_available;
                        station.num_docks_available = status.num_docks_available;
                    });
        }

        stationList.clear();
        stationList.addAll(stations.data.stations);
        return stationList;
    }
}

