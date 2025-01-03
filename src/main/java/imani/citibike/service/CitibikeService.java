package imani.citibike.service;

import imani.citibike.json.StationStatuses;
import imani.citibike.json.Stations;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;


public interface CitibikeService {
    @GET("station_information.json")
    Single<Stations> getStationInfoResponse(
    );

    @GET("station_status.json")
    Single<StationStatuses> getStationStatusResponse(
    );
}

