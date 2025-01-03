package imani.citibike.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;
import imani.citibike.json.DataStations;
import imani.citibike.json.Station;
import imani.citibike.json.StationLocator;
import imani.citibike.json.StationStatus;
import imani.citibike.service.CitibikeService;
import imani.citibike.service.CitibikeServiceFactory;
import imani.citibike.service.StationUpdaterService;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static imani.citibike.json.StationLocator.findClosestStation;

public class CitibikeRequestHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, CitibikeRequestHandler.CitiBikeResponse> {

    private final CitibikeService citibikeService = new CitibikeServiceFactory().getService();
    private final CitibikeServiceFactory citibikeServiceFactory = new CitibikeServiceFactory();
    private final Gson gson = new Gson();

    @Override
    public CitiBikeResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        CitiBikeRequest request = gson.fromJson(event.getBody(), CitiBikeRequest.class);

        citibikeServiceFactory.getService();

        ArrayList<Station> stationList = new ArrayList<>();
        StationUpdaterService sus = new StationUpdaterService(citibikeService);
        sus.updateStationListWithStatus(stationList);

        Station startStation = findClosestStation(
                request.from.lon,
                request.from.lat,
                true
        );

        Station endStation = findClosestStation(
                request.to.lon,
                request.to.lat,
                false
        );

        return new CitiBikeResponse(request.from, startStation, endStation, request.to);

    }

    public record CitiBikeRequest(Location from, Location to) {}

    public record Location(double lat, double lon) {}

    public record CitiBikeResponse(Location from, Station start, Station end, Location to) {
        public CitiBikeResponse(String message) {
            this(null, null, null, null);
        }
    }
}
