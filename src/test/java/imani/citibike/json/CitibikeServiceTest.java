package imani.citibike.json;

import imani.citibike.json.Station;
import imani.citibike.json.StationStatus;
import imani.citibike.json.StationStatuses;
import imani.citibike.json.Stations;
import imani.citibike.service.CitibikeService;
import imani.citibike.service.CitibikeServiceFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CitibikeServiceTest {

    @Test
    public void stationInfoResponse() {
        //given
        CitibikeService citibikeService = new CitibikeServiceFactory().getService();

        //when
        Stations stations = citibikeService.getStationInfoResponse().blockingGet();

        //then
        Station station = stations.data.stations.get(0);
        assertNotNull(station.name);
        assertNotNull(station.station_id);
        assertNotEquals(0.0, station.lon);
        assertNotEquals(0.0, station.lat);
        assertNotEquals(0.0, station.capacity);
    }

    @Test
    public void stationStatusResponse() {
        //given
        CitibikeService citibikeService = new CitibikeServiceFactory().getService();

        //when
        StationStatuses stationStatuses = citibikeService.getStationStatusResponse().blockingGet();


        //then
        StationStatus stationStatus = stationStatuses.data.stations.get(0);
        assertNotNull(stationStatus.station_id);

    }

    @Test
    public void closestBikePickUp() {

    }

    @Test
    public void closestBikeDropOff() {

    }
}
