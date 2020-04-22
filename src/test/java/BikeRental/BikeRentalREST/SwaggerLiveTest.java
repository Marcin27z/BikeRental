package BikeRental.BikeRentalREST;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.jayway.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SwaggerLiveTest {

    @LocalServerPort
    int port;

    @Test
    void whenVerifySpringFoxIsWorking_thenOK() {
        given().port(port).get("/v2/api-docs")
                .then().assertThat().statusCode(200);
    }
}
