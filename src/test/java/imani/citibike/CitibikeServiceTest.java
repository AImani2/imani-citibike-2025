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
        Station station = stations.stations[0];
        assertNotNull(station.name);
        assertNotNull(station.station_id);
        assertNotNull(station.lon);
        assertNotNull(station.lat);
    }

    @Test
    public void stationStatusResponse() {
        //given
        CitibikeService citibikeService = new CitibikeServiceFactory().getService();

        //when
        Stations stations = citibikeService.getStationStatusResponse().blockingGet();
        System.out.println("Response: " + stations);

        //then
        Station station = stations.stations[0];
        assertNotNull(station.num_docks_available);
        assertNotNull(station.num_bikes_available);
    }

    @Test
    public void closestBikePickUp() {

    }

    @Test
    public void closestBikeDropOff() {

    }
}
