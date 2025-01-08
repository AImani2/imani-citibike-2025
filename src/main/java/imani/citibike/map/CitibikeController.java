package imani.citibike.map;

import imani.citibike.json.Station;
import imani.citibike.json.StationLocator;
import imani.citibike.service.CitibikeService;
import imani.citibike.service.CitibikeServiceFactory;
import imani.citibike.service.StationUpdaterService;
import org.jxmapviewer.viewer.GeoPosition;

import java.util.ArrayList;
import java.util.List;

public class CitibikeController {
    private final CitibikeService citibikeService = new CitibikeServiceFactory().getService();
    private StationUpdaterService sus = new StationUpdaterService(citibikeService);
    private StationLocator stationLocator = new StationLocator(sus, citibikeService);
    private final CitibikeComponent citibikeComponent;
    private GeoPosition toPoint;
    private GeoPosition fromPoint;
    private ArrayList<Station> resultStations = new ArrayList<>();
    private Station startStation;
    private Station endStation;
    private GeoPosition stationGeo;

    public CitibikeController(CitibikeComponent citibikeComponent) {
        this.citibikeComponent = citibikeComponent;
    }

    public void setPoints(GeoPosition toPosition, GeoPosition fromPosition) {
        this.toPoint = toPosition;
        this.fromPoint = fromPosition;
    }

    public void clearPoints() {
        toPoint = null;
        fromPoint = null;
    }

    public ArrayList<Station> findClosestStations() {

        endStation = stationLocator.findClosestStation(toPoint.getLongitude(), toPoint.getLatitude(), false, sus);
        startStation = stationLocator.findClosestStation(fromPoint.getLongitude(), fromPoint.getLatitude(), true, sus);
        resultStations.set(0, startStation);
        resultStations.set(1, endStation);
        return resultStations;
    }

    public GeoPosition getStationGeoPosition(Station station) {
        stationGeo = new GeoPosition(station.lat, station.lon);
        return stationGeo;
    }

}
