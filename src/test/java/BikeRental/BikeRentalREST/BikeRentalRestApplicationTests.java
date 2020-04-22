package BikeRental.BikeRentalREST;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.jayway.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BikeRentalRestApplicationTests {

    @LocalServerPort
    int port;

	@Test
	void contextLoads() {
		given().port(port).get("/").then().assertThat().extract();
	}
}
