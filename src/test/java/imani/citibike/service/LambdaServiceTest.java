package imani.citibike.service;

import imani.citibike.aws.CitibikeRequestHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LambdaServiceTest {

    @Test
    public void getLambda() {
        //given
        CitibikeRequestHandler.Location fromLocation = new CitibikeRequestHandler.Location(40.8211, -73.9359);
        CitibikeRequestHandler.Location toLocation = new CitibikeRequestHandler.Location(40.7190, -73.9585);
        CitibikeRequestHandler.CitiBikeRequest request
                = new CitibikeRequestHandler.CitiBikeRequest(fromLocation, toLocation);

        //when
        try {
            LambdaService lambdaService = new LambdaServiceFactory().getService();
            CitibikeRequestHandler.CitiBikeResponse response = lambdaService.getLambda(request).blockingGet();
            assertNotNull(response);
            assertNotNull(response.end());
            assertNotNull(response.start());
            assertNotNull(response.from());
            assertNotNull(response.to());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
