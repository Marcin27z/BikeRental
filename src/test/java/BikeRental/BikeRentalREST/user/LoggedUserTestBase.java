package BikeRental.BikeRentalREST.user;

import com.jayway.restassured.specification.RequestSpecification;
import org.springframework.boot.web.server.LocalServerPort;

import static com.jayway.restassured.RestAssured.given;

public class LoggedUserTestBase extends UserTestBase {

    @LocalServerPort
    int port;

    private User loggedUser;
    private String token;

    void loginAsAdmin() {
        login(true);
    }

    void loginAsUnprivilegedUser() {
        login(false);
    }

    void login(boolean isAdmin){
        loggedUser = createUser(isAdmin, true);
        token = userService.login(loggedUser.getLogin(), loggedUser.getPassword());
    }

    void logout() {
        userRepository.delete(loggedUser);
    }

    RequestSpecification givenLogged() {
        return given().port(port)
                .authentication().oauth2(getToken());
    }

    String getToken() {
        return token;
    }
}
