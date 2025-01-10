package imani.citibike.map;

import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import imani.citibike.aws.CitibikeRequestHandler;
import imani.citibike.json.Stations;
import imani.citibike.json.StationsCache;
import imani.citibike.service.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jxmapviewer.viewer.GeoPosition;

public class CitibikeController {
    private GeoPosition toPoint;
    private GeoPosition fromPoint;
    private CitibikeRequestHandler.Location fromLocation;
    private CitibikeRequestHandler.Location toLocation;
    private GeoPosition startStation;
    private GeoPosition endStation;
    private LambdaService lambdaService;

    public CitibikeController(LambdaService lambdaService) {
        this.lambdaService = lambdaService;
    }

    public void setPoints(GeoPosition toPosition, GeoPosition fromPosition) {
        this.toPoint = toPosition;
        this.fromPoint = fromPosition;
    }

    public void clearPoints() {
        toPoint = null;
        fromPoint = null;
    }

    public void findClosestStations() {

        fromLocation = new CitibikeRequestHandler.Location(fromPoint.getLatitude(), fromPoint.getLongitude());
        toLocation = new CitibikeRequestHandler.Location(toPoint.getLatitude(), toPoint.getLongitude());

        CitibikeRequestHandler.CitiBikeRequest request
                = new CitibikeRequestHandler.CitiBikeRequest(fromLocation, toLocation);

        Disposable disposable = lambdaService.getLambda(request)
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        response -> {
                            if (response.start() != null && response.end() != null) {
                                endStation = new GeoPosition(response.end().lat, response.end().lon);
                                startStation = new GeoPosition(response.start().lat, response.start().lon);
                            }
                        },
                        Throwable::printStackTrace
                );
    }

    public GeoPosition getStartStation() {
        return startStation;
    }

    public GeoPosition getEndStation() {
        return endStation;
    }


}
