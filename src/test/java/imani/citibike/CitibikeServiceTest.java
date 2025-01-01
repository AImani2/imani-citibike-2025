package imani.citibike;

import org.junit.jupiter.api.Test;
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
        assertNotNull(station.lon);
        assertNotNull(station.lat);
        assertNotNull(station.capacity);
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
        assertNotNull(stationStatus.num_bikes_available);
        assertNotNull(stationStatus.num_docks_available);


    }

    @Test
    public void closestBikePickUp() {

    }

    @Test
    public void closestBikeDropOff() {

    }
}
