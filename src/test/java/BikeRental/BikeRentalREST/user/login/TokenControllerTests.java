package BikeRental.BikeRentalREST.user.login;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.UserTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TokenControllerTests extends UserTestBase {
    private final static String TOKEN_ENDPOINT = "/token";

    private final static String FAKE_USERNAME = "fake";
    private final static String FAKE_PASSWORD = "fake";

    @LocalServerPort
    int port;

    @AfterEach
    void tearDown() {
        deleteAllUsers();
    }

    @Test
    void whenAskForTokenWithBadCredentialsThenGotErrorMessage() {
        String response = given().port(port)
                .param("username", FAKE_USERNAME)
                .param("password", FAKE_PASSWORD)
                .post(TOKEN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .asString();

        assertEquals("no token found", response);
    }

    @Test
    void whenAskForTokenWithGoodCredentialsThenGotTokenAndNoErrorMessage() {
        User user = createUser();

        String response = given().port(port)
                .param("username", user.getLogin())
                .param("password", user.getPassword())
                .post(TOKEN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .asString();

        assertNotEquals("no token found", response);
    }
}

