package imani.citibike.json;

import imani.citibike.service.CitibikeService;
import imani.citibike.service.CitibikeServiceFactory;
import imani.citibike.service.StationUpdaterService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StationLocatorTest {

    @Test
    public void findClosestPickUpStation() {
            //given
            StationsCache stationsCache = new StationsCache();
            CitibikeService citibikeService = new CitibikeServiceFactory().getService();
            StationUpdaterService sus = new StationUpdaterService(citibikeService, stationsCache);
            StationLocator stationLocator = new StationLocator();

            try {
                //when
                Station startStation = stationLocator.findClosestStation(-73.9359, 40.8211, true, sus);

                //then
                assertNotNull(startStation);
                assertTrue(startStation.num_bikes_available > 0);
                assertEquals("Lenox Ave & W 146 St", startStation.name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

    }

    @Test
    public void findClosestDockingStation() {
        //given
        StationsCache stationsCache = new StationsCache();
        CitibikeService citibikeService = new CitibikeServiceFactory().getService();
        StationUpdaterService sus = new StationUpdaterService(citibikeService, stationsCache);
        StationLocator stationLocator = new StationLocator();

        try {
            //when
            Station endStation = stationLocator.findClosestStation(-73.9585, 40.7190, true, sus);

            //then
            assertNotNull(endStation);
            assertTrue(endStation.num_docks_available > 0);
            assertEquals("Berry St & N 8 St", endStation.name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
