package imani.citibike.aws;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.jupiter.api.Test;
import com.amazonaws.services.lambda.runtime.Context;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CitibikeRequestHandlerTest {

    @Test
    void handleRequest() {

        //given
        CitibikeRequestHandler handler;
        CitibikeRequestHandler.CitiBikeResponse response;
        String body = """
    {
      "from": {
        "lat": 40.8211,
        "lon": -73.9359
      },
      "to": {
        "lat": 40.7190,
        "lon": -73.9585
      }
    }
    """;

        Context context = mock(Context.class);
        APIGatewayProxyRequestEvent event = mock(APIGatewayProxyRequestEvent.class);
        when(event.getBody()).thenReturn(body);

        try {
            handler = new CitibikeRequestHandler();
            response = handler.handleRequest(event, context);
            assertEquals(response.start().name, "Lenox Ave & W 146 St");
            assertEquals(response.end().name, "Berry St & N 8 St");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}