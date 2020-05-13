package BikeRental.BikeRentalREST.user;

import BikeRental.BikeRentalREST.user.login.LoginInfo;
import com.jayway.restassured.specification.RequestSpecification;
import org.springframework.boot.web.server.LocalServerPort;

import static com.jayway.restassured.RestAssured.given;

public class LoggedUserTestBase extends UserTestBase {

    @LocalServerPort
    int port;

    private User loggedUser;
    private String token;

    public void loginAsAdmin() {
        login(true);
    }

    public void loginAsUnprivilegedUser() {
        login(false);
    }

    void login(boolean isAdmin){
        this.loggedUser = createUser(isAdmin, true);

        LoginInfo loginInfo = userService.login(loggedUser.getLogin(), loggedUser.getPassword());
        token = loginInfo.getToken();
    }

    public void logout() {
        userRepository.delete(loggedUser);
    }

    public RequestSpecification givenLogged() {
        return given().port(port)
                .authentication().oauth2(getToken());
    }

    public String getToken() {
        return token;
    }
}
