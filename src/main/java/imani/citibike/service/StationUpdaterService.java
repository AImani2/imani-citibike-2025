package imani.citibike.service;

import imani.citibike.json.Station;
import imani.citibike.json.StationStatus;

import java.util.ArrayList;
import java.util.List;

public class StationUpdaterService {
    private final CitibikeService citibikeService;

    public StationUpdaterService(CitibikeService citibikeService) {
        this.citibikeService = citibikeService;
    }

    public void updateStationListWithStatus(ArrayList<Station> stationList) {
        var stationInfoResponse = citibikeService.getStationInfoResponse()
                .blockingGet();

        var stationStatusResponse = citibikeService.getStationStatusResponse()
                .blockingGet();

        List<StationStatus> statuses = stationStatusResponse.data.stations;

        for (Station station : stationInfoResponse.data.stations) {
            statuses.stream()
                    .filter(status -> status.station_id.equals(station.station_id))
                    .findFirst()
                    .ifPresent(status -> {
                        station.num_bikes_available = status.num_bikes_available;
                        station.num_docks_available = status.num_docks_available;
                    });
        }

        stationList.clear();
        stationList.addAll(stationInfoResponse.data.stations);
    }
}

