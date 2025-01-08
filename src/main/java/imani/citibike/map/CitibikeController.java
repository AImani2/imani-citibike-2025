package imani.citibike.map;

import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import imani.citibike.aws.CitibikeRequestHandler;
import imani.citibike.json.Station;
import imani.citibike.json.StationLocator;
import imani.citibike.service.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jxmapviewer.viewer.GeoPosition;

import java.util.ArrayList;
import java.util.List;

public class CitibikeController {
    private CitibikeComponent citibikeComponent;
    private GeoPosition toPoint;
    private GeoPosition fromPoint;
    private CitibikeRequestHandler.Location fromLocation;
    private CitibikeRequestHandler.Location toLocation;
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

        fromLocation = new CitibikeRequestHandler.Location(fromPoint.getLatitude(), fromPoint.getLongitude());
        toLocation = new CitibikeRequestHandler.Location(toPoint.getLatitude(), toPoint.getLongitude());
        CitibikeRequestHandler.CitiBikeRequest request = new CitibikeRequestHandler.CitiBikeRequest(fromLocation, toLocation);

        LambdaService lambdaService = new LambdaServiceFactory().getService();
        Disposable disposable = lambdaService.getLambda(request)
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        response -> {
                            endStation = response.end();
                            startStation = response.start();
                        },
                        Throwable::printStackTrace
                );
        resultStations.set(0, startStation);
        resultStations.set(1, endStation);
        return resultStations;
    }

    public GeoPosition getStationGeoPosition(Station station) {
        stationGeo = new GeoPosition(station.lat, station.lon);
        return stationGeo;
    }

}
