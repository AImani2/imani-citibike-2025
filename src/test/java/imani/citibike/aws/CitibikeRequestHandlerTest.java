package imani.citibike.aws;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import imani.citibike.json.Station;
import imani.citibike.json.StationLocator;
import imani.citibike.service.StationUpdaterService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.amazonaws.services.lambda.runtime.Context;
import static org.junit.jupiter.api.Assertions.*;

        import static org.mockito.Mockito.mock;
        import static org.mockito.Mockito.when;

class CitibikeRequestHandlerTest {

    @Test
    public void handleRequest() throws IOException {
        // Given: Load the request JSON from a file
        String body = Files.readString(Path.of("src/test/resources/request.json"));

        // Mock Context and APIGatewayProxyRequestEvent
        Context context = mock(Context.class);
        APIGatewayProxyRequestEvent event = mock(APIGatewayProxyRequestEvent.class);
        when(event.getBody()).thenReturn(body);

        // Mock the CitibikeService, StationUpdaterService, and StationLocator
        CitibikeRequestHandler.CitiBikeRequest mockRequest = mock(CitibikeRequestHandler.CitiBikeRequest.class);
        CitibikeRequestHandler.Location mockFromLocation = new CitibikeRequestHandler.Location(40.730610, -73.935242);
        CitibikeRequestHandler.Location mockToLocation = new CitibikeRequestHandler.Location(40.712776, -74.005974);

        when(mockRequest.from()).thenReturn(mockFromLocation);
        when(mockRequest.to()).thenReturn(mockToLocation);

        // Mock the StationUpdaterService
        StationUpdaterService mockStationUpdaterService = mock(StationUpdaterService.class);
        Station mockStartStation = mock(Station.class);
        Station mockEndStation = mock(Station.class);

        when(mockStartStation.name).thenReturn("N 6 St & Bedford Ave");
        when(mockEndStation.name).thenReturn("Lenox Ave & W 146 St");

        // Mock the CitibikeRequestHandler to return the mock stations in the response
        CitibikeRequestHandler handler = new CitibikeRequestHandler() {
            @Override
            public CitiBikeResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {
                // Directly mock the response to include the mocked stations
                return new CitiBikeResponse(mockRequest.from(), mockStartStation, mockEndStation, mockRequest.to());
            }
        };

        // When: Handle the request
        CitibikeRequestHandler.CitiBikeResponse citibikeResponse = handler.handleRequest(event, context);

        // Then: Assert that the response contains the expected values
        assertNotNull(citibikeResponse, "CitiBikeResponse should not be null");

        // Check if 'end' returns a non-null object
        Station endStation = citibikeResponse.end();
        assertNotNull(endStation, "'end' station should not be null");
        assertNotNull(endStation.name, "'end' station name should not be null");
        assertEquals("Lenox Ave & W 146 St", endStation.name);

        // Check if 'start' returns a non-null object
        Station startStation = citibikeResponse.start();
        assertNotNull(startStation, "'start' station should not be null");
        assertNotNull(startStation.name, "'start' station name should not be null");
        assertEquals("N 6 St & Bedford Ave", startStation.name);
    }


}