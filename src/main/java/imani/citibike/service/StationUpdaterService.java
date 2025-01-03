package imani.citibike.service;

import imani.citibike.json.Station;
import imani.citibike.json.StationStatus;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class StationUpdaterService {
    private final CitibikeService citibikeService;

    public StationUpdaterService(CitibikeService citibikeService) {
        this.citibikeService = citibikeService;
    }

    public void updateStationListWithStatus(ArrayList<Station> stationList) {
        var stationInfoResponse = citibikeService.getStationInfoResponse()
                .observeOn(Schedulers.io())
                .blockingGet();

        var stationStatusResponse = citibikeService.getStationStatusResponse()
                .observeOn(Schedulers.io())
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

