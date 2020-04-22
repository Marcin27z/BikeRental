package BikeRental.BikeRentalREST.user.login;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.jayway.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokenControllerTests {
    private final static String TOKEN_ENDPOINT = "/token";

    private final static String FAKE_USERNAME = "fake";
    private final static String FAKE_PASSWORD = "fake";

    private final static String REAL_USERNAME = "real";
    private final static String REAL_PASSWORD = "password";
    private final static String REAL_EMAIL = "mail@mail.com";
    private final static String REAL_PHONE_NUMBER = "55555555";

    @LocalServerPort
    int port;

    @Autowired
    UserRepository userRepository;

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

        assert (response.equals("no token found"));
    }

    @AfterEach
    void removeUsers() {
        userRepository.deleteAll();
    }

    @Test
    void whenAskForTokenWithGoodCredentialsThenGotTokenAndNoErrorMessage() {
        User user = new User(REAL_EMAIL, REAL_USERNAME, REAL_PASSWORD, REAL_PHONE_NUMBER);
        userRepository.save(user);

        String response = given().port(port)
                .param("username", REAL_USERNAME)
                .param("password", REAL_PASSWORD)
                .post(TOKEN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .asString();

        assert (!response.equals("no token found"));
    }
}

