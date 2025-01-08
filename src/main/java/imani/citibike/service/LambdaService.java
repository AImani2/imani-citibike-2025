package imani.citibike.service;

import imani.citibike.aws.CitibikeRequestHandler;
import imani.citibike.aws.CitibikeRequestHandler.CitiBikeResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LambdaService {
    @POST("/")
    Single<CitiBikeResponse> getLambda(@Body CitibikeRequestHandler.CitiBikeRequest request);
}
