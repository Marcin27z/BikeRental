package BikeRental.BikeRentalREST.user.login;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.UserTestBase;
import org.apache.commons.lang3.StringUtils;
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
    void whenAskForTokenWithBadCredentialsThenGotLoginInfoWithBlankToken() {
        LoginInfo loginInfo = given().port(port)
                .param("username", FAKE_USERNAME)
                .param("password", FAKE_PASSWORD)
                .post(TOKEN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(LoginInfo.class);

        assertEquals(StringUtils.EMPTY, loginInfo.getToken());
    }

    @Test
    void whenAskForTokenWithGoodCredentialsThenGotLoginInfoWithTokenAndUserPrivileges() {
        User user = createUser();

        LoginInfo loginInfo = given().port(port)
                .param("username", user.getLogin())
                .param("password", user.getPassword())
                .post(TOKEN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(LoginInfo.class);

        assertNotEquals(StringUtils.EMPTY, loginInfo.getToken());
        assertEquals(user.isAdmin(), loginInfo.isAdmin());
    }
}

