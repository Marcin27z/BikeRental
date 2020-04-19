package BikeRental.BikeRentalREST;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class SwaggerLiveTest {
    private static final String URL_PREFIX = "http://localhost:8080";

    @Test
    public void whenVerifySpringFoxIsWorking_thenOK() {
        final Response response = RestAssured.get(URL_PREFIX + "/v2/api-docs");
        assertEquals(200, response.statusCode());
        System.out.println(response.asString());

    }
}