package imani.citibike;

import imani.citibike.json.Stations;
import imani.citibike.service.CitibikeService;
import imani.citibike.service.CitibikeServiceFactory;

public class Main {
    public static void main(String[] args) {

        //given
        CitibikeService citibikeService = new CitibikeServiceFactory().getService();

        //when
        Stations stations = citibikeService.getStationInfoResponse().blockingGet();
        System.out.println(stations);

        // copying in Rijks musium
        // make a json folder
        // data
        // data collection
        // station
        // use the json files he sent
        // service interface
        // service factory
        // citibike service test


        // use records

    }
}